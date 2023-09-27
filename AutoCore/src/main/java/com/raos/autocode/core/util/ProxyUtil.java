package com.raos.autocode.core.util;

import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BooleanSupplier;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Utilities for proxy
 * 
 * Full credit for designing this class goes to Oracle Developers and OpenJDK
 * developers CODE TAKEN FROM JDK 16
 * 
 * @author aditya
 *
 */
// For Java 11 Users... i scraped the java 16 api to get this stuff
@ClassPreamble(author = "Authors of Oracle JDK 16", date = "Unknown")
public final class ProxyUtil {
	private ProxyUtil() {
	}

	/**
	 * A cache of Method -> MethodHandle for default methods.
	 */
	private static final ClassValue<ConcurrentHashMap<Method, MethodHandle>> DEFAULT_METHODS_MAP = new ClassValue<>() {
		@Override
		protected ConcurrentHashMap<Method, MethodHandle> computeValue(Class<?> type) {
			return new ConcurrentHashMap<>(4);
		}
	};

	private static ConcurrentHashMap<Method, MethodHandle> defaultMethodMap(Class<?> proxyClass) {
		assert Proxy.isProxyClass(proxyClass);
		return DEFAULT_METHODS_MAP.get(proxyClass);
	}

	static final Object[] EMPTY_ARGS = new Object[0];

	static MethodHandle defaultMethodHandle(Class<? extends Proxy> proxyClass, Method method) {
		// lookup the cached method handle
		ConcurrentHashMap<Method, MethodHandle> methods = defaultMethodMap(proxyClass);
		MethodHandle superMH = methods.get(method);
		if (superMH == null) {
			MethodType type = methodType(method.getReturnType(), method.getParameterTypes());
			Class<?> proxyInterface = findProxyInterfaceOrElseThrow(proxyClass, method);
			MethodHandle dmh;
			try {
				dmh = proxyClassLookup(proxyClass).findSpecial(proxyInterface, method.getName(), type, proxyClass)
						.withVarargs(false);
			} catch (IllegalAccessException | NoSuchMethodException e) {
				// should not reach here
				throw new InternalError(e);
			}
			// this check can be turned into assertion as it is guaranteed to succeed by the
			// virtue of
			// looking up a default (instance) method declared or inherited by
			// proxyInterface
			// while proxyClass implements (is a subtype of) proxyInterface ...
			assert ((BooleanSupplier) () -> {
				try {
					// make sure that the method type matches
					dmh.asType(type.insertParameterTypes(0, proxyClass));
					return true;
				} catch (WrongMethodTypeException e) {
					return false;
				}
			}).getAsBoolean() : "Wrong method type";
			// change return type to Object
			MethodHandle mh = dmh.asType(dmh.type().changeReturnType(Object.class));
			// wrap any exception thrown with InvocationTargetException
			mh = MethodHandles.catchException(mh, Throwable.class, InvocationException.wrapMH());
			// spread array of arguments among parameters (skipping 1st parameter - target)
			mh = mh.asSpreader(1, Object[].class, type.parameterCount());
			// change target type to Object
			mh = mh.asType(MethodType.methodType(Object.class, Object.class, Object[].class));

			// push MH into cache
			MethodHandle cached = methods.putIfAbsent(method, mh);
			if (cached != null) {
				superMH = cached;
			} else {
				superMH = mh;
			}
		}
		return superMH;
	}

	/**
	 * Finds the first proxy interface that declares the given method directly or
	 * indirectly.
	 *
	 * @throws IllegalArgumentException if not found
	 */
	private static Class<?> findProxyInterfaceOrElseThrow(Class<?> proxyClass, Method method) {
		Class<?> declaringClass = method.getDeclaringClass();
		if (!declaringClass.isInterface()) {
			throw new IllegalArgumentException("\"" + method + "\" is not a method declared in the proxy class");
		}

		List<Class<?>> proxyInterfaces = Arrays.asList(proxyClass.getInterfaces());
		// the method's declaring class is a proxy interface
		if (proxyInterfaces.contains(declaringClass))
			return declaringClass;

		// find the first proxy interface that inherits the default method
		// i.e. the declaring class of the default method is a superinterface
		// of the proxy interface
		Deque<Class<?>> deque = new ArrayDeque<>();
		Set<Class<?>> visited = new HashSet<>();
		boolean indirectMethodRef = false;
		for (Class<?> proxyIntf : proxyInterfaces) {
			assert proxyIntf != declaringClass;
			visited.add(proxyIntf);
			deque.add(proxyIntf);

			// for each proxy interface, traverse its subinterfaces with
			// breadth-first search to find a subinterface declaring the
			// default method
			Class<?> c;
			while ((c = deque.poll()) != null) {
				if (c == declaringClass) {
					try {
						// check if this method is the resolved method if referenced from
						// this proxy interface (i.e. this method is not implemented
						// by any other superinterface)
						Method m = proxyIntf.getMethod(method.getName(), method.getParameterTypes());
						if (m.getDeclaringClass() == declaringClass) {
							return proxyIntf;
						}
						indirectMethodRef = true;
					} catch (NoSuchMethodException e) {
					}

					// skip traversing its superinterfaces
					// another proxy interface may extend it and so
					// the method's declaring class is left unvisited.
					continue;
				}
				// visit all superinteraces of one proxy interface to find if
				// this proxy interface inherits the method directly or indirectly
				visited.add(c);
				for (Class<?> superIntf : c.getInterfaces()) {
					if (!visited.contains(superIntf) && !deque.contains(superIntf)) {
						if (superIntf == declaringClass) {
							// fast-path as the matching subinterface is found
							deque.addFirst(superIntf);
						} else {
							deque.add(superIntf);
						}
					}
				}
			}
		}

		throw new IllegalArgumentException(
				"\"" + method + (indirectMethodRef ? "\" is overridden directly or indirectly by the proxy interfaces"
						: "\" is not a method declared in the proxy class"));
	}

	/**
	 * This method invokes the proxy's proxyClassLookup method to get a Lookup on
	 * the proxy class.
	 *
	 * @return a lookup for proxy class of this proxy instance
	 */
	private static MethodHandles.Lookup proxyClassLookup(Class<?> proxyClass) {

		return MethodHandles.lookup();
	}

	/**
	 * Internal exception type to wrap the exception thrown by the default method so
	 * that it can distinguish CCE and NPE thrown due to the arguments incompatible
	 * with the method signature.
	 */
	static class InvocationException extends ReflectiveOperationException {
		private static final long serialVersionUID = 0L;

		InvocationException(Throwable cause) {
			super(cause);
		}

		/**
		 * Wraps given cause with InvocationException and throws it.
		 */
		static Object wrap(Throwable cause) throws InvocationException {
			throw new InvocationException(cause);
		}

		static MethodHandle wrapMethodHandle;

		static MethodHandle wrapMH() {
			MethodHandle mh = wrapMethodHandle;
			if (mh == null) {
				try {
					wrapMethodHandle = mh = MethodHandles.lookup().findStatic(InvocationException.class, "wrap",
							MethodType.methodType(Object.class, Throwable.class));
				} catch (NoSuchMethodException | IllegalAccessException e) {
					throw new InternalError(e);
				}
			}
			return mh;
		}
	}

	/**
	 * Invokes the specified default method on the given {@code proxy} instance with
	 * the given parameters. The given {@code method} must be a default method
	 * declared in a proxy interface of the {@code proxy}'s class or inherited from
	 * its superinterface directly or indirectly.
	 * <p>
	 * Invoking this method behaves as if {@code invokespecial} instruction executed
	 * from the proxy class, targeting the default method in a proxy interface. This
	 * is equivalent to the invocation: {@code X.super.m(A* a)} where {@code X} is a
	 * proxy interface and the call to {@code X.super::m(A*)} is resolved to the
	 * given {@code method}.
	 * <p>
	 * Examples: interface {@code A} and {@code B} both declare a default
	 * implementation of method {@code m}. Interface {@code C} extends {@code A} and
	 * inherits the default method {@code m} from its superinterface {@code A}.
	 *
	 * <blockquote>
	 * 
	 * <pre>{@code
	 * interface A {
	 * 	default T m(A a) {
	 * 		return t1;
	 * 	}
	 * }
	 * 
	 * interface B {
	 * 	default T m(A a) {
	 * 		return t2;
	 * 	}
	 * }
	 * 
	 * interface C extends A {
	 * }
	 * }</pre>
	 * 
	 * </blockquote>
	 *
	 * The following creates a proxy instance that implements {@code A} and invokes
	 * the default method {@code A::m}.
	 *
	 * <blockquote>
	 * 
	 * <pre>{@code
	 * Object proxy = Proxy.newProxyInstance(loader, new Class<?>[] { A.class }, (o, m, params) -> {
	 * 	if (m.isDefault()) {
	 * 		// if it's a default method, invoke it
	 * 		return InvocationHandler.invokeDefault(o, m, params);
	 * 	}
	 * });
	 * }</pre>
	 * 
	 * </blockquote>
	 *
	 * If a proxy instance implements both {@code A} and {@code B}, both of which
	 * provides the default implementation of method {@code m}, the invocation
	 * handler can dispatch the method invocation to {@code A::m} or {@code B::m}
	 * via the {@code invokeDefault} method. For example, the following code
	 * delegates the method invocation to {@code B::m}.
	 *
	 * <blockquote>
	 * 
	 * <pre>{@code
	 * Object proxy = Proxy.newProxyInstance(loader, new Class<?>[] { A.class, B.class }, (o, m, params) -> {
	 * 	if (m.getName().equals("m")) {
	 * 		// invoke B::m instead of A::m
	 * 		Method bMethod = B.class.getMethod(m.getName(), m.getParameterTypes());
	 * 		return InvocationHandler.invokeDefault(o, bMethod, params);
	 * 	}
	 * });
	 * }</pre>
	 * 
	 * </blockquote>
	 *
	 * If a proxy instance implements {@code C} that inherits the default method
	 * {@code m} from its superinterface {@code A}, then the interface method
	 * invocation on {@code "m"} is dispatched to the invocation handler's
	 * {@link #invoke(Object, Method, Object[]) invoke} method with the
	 * {@code Method} object argument representing the default method {@code A::m}.
	 *
	 * <blockquote>
	 * 
	 * <pre>{@code
	 * Object proxy = Proxy.newProxyInstance(loader, new Class<?>[] { C.class }, (o, m, params) -> {
	 * 	if (m.isDefault()) {
	 * 		// behaves as if calling C.super.m(params)
	 * 		return InvocationHandler.invokeDefault(o, m, params);
	 * 	}
	 * });
	 * }</pre>
	 * 
	 * </blockquote>
	 *
	 * The invocation of method {@code "m"} on this {@code proxy} will behave as if
	 * {@code C.super::m} is called and that is resolved to invoking {@code A::m}.
	 * <p>
	 * Adding a default method, or changing a method from abstract to default may
	 * cause an exception if an existing code attempts to call {@code invokeDefault}
	 * to invoke a default method.
	 *
	 * For example, if {@code C} is modified to implement a default method
	 * {@code m}:
	 *
	 * <blockquote>
	 * 
	 * <pre>{@code
	 * interface C extends A {
	 * 	default T m(A a) {
	 * 		return t3;
	 * 	}
	 * }
	 * }</pre>
	 * 
	 * </blockquote>
	 *
	 * The code above that creates proxy instance {@code proxy} with the modified
	 * {@code C} will run with no exception and it will result in calling
	 * {@code C::m} instead of {@code A::m}.
	 * <p>
	 * The following is another example that creates a proxy instance of {@code C}
	 * and the invocation handler calls the {@code invokeDefault} method to invoke
	 * {@code A::m}:
	 *
	 * <blockquote>
	 * 
	 * <pre>{@code
	 * C c = (C) Proxy.newProxyInstance(loader, new Class<?>[] { C.class },
	 *         (o, m, params) -> {
	 *             if (m.getName().equals("m")) {
	 *                 // IllegalArgumentException thrown as {@code A::m} is not a method
	 *                 // inherited from its proxy interface C
	 *                 Method aMethod = A.class.getMethod(m.getName(), m.getParameterTypes());
	 *                 return InvocationHandler.invokeDefault(o, aMethod params);
	 *             }
	 *         });
	 * c.m(...);
	 * }</pre>
	 * 
	 * </blockquote>
	 *
	 * The above code runs successfully with the old version of {@code C} and
	 * {@code A::m} is invoked. When running with the new version of {@code C}, the
	 * above code will fail with {@code IllegalArgumentException} because {@code C}
	 * overrides the implementation of the same method and {@code A::m} is not
	 * accessible by a proxy instance.
	 *
	 * @apiNote The {@code proxy} parameter is of type {@code Object} rather than
	 *          {@code Proxy} to make it easy for
	 *          {@link InvocationHandler#invoke(Object, Method, Object[])
	 *          InvocationHandler::invoke} implementation to call directly without
	 *          the need of casting.
	 *
	 * @param proxy  the {@code Proxy} instance on which the default method to be
	 *               invoked
	 * @param method the {@code Method} instance corresponding to a default method
	 *               declared in a proxy interface of the proxy class or inherited
	 *               from its superinterface directly or indirectly
	 * @param args   the parameters used for the method invocation; can be
	 *               {@code null} if the number of formal parameters required by the
	 *               method is zero.
	 * @return the value returned from the method invocation
	 *
	 * @throws IllegalArgumentException if any of the following conditions is
	 *                                  {@code true}:
	 *                                  <ul>
	 *                                  <li>{@code proxy} is not
	 *                                  {@linkplain Proxy#isProxyClass(Class) a
	 *                                  proxy instance}; or</li>
	 *                                  <li>the given {@code method} is not a
	 *                                  default method declared in a proxy interface
	 *                                  of the proxy class and not inherited from
	 *                                  any of its superinterfaces; or</li>
	 *                                  <li>the given {@code method} is overridden
	 *                                  directly or indirectly by the proxy
	 *                                  interfaces and the method reference to the
	 *                                  named method never resolves to the given
	 *                                  {@code method}; or</li>
	 *                                  <li>the length of the given {@code args}
	 *                                  array does not match the number of
	 *                                  parameters of the method to be invoked;
	 *                                  or</li>
	 *                                  <li>any of the {@code args} elements fails
	 *                                  the unboxing conversion if the corresponding
	 *                                  method parameter type is a primitive type;
	 *                                  or if, after possible unboxing, any of the
	 *                                  {@code args} elements cannot be assigned to
	 *                                  the corresponding method parameter
	 *                                  type.</li>
	 *                                  </ul>
	 * @throws IllegalAccessException   if the declaring class of the specified
	 *                                  default method is inaccessible to the caller
	 *                                  class
	 * @throws NullPointerException     if {@code proxy} or {@code method} is
	 *                                  {@code null}
	 * @throws Throwable                anything thrown by the default method
	 * 
	 * @since 16
	 * @jvms 5.4.3. Method Resolution
	 */
	public static Object invokeDefault(Object proxy, Method method, Object... args) throws Throwable {
		Objects.requireNonNull(proxy);
		Objects.requireNonNull(method);

		// verify that the object is actually a proxy instance
		if (!Proxy.isProxyClass(proxy.getClass())) {
			throw new IllegalArgumentException("'proxy' is not a proxy instance");
		}
		if (!method.isDefault()) {
			throw new IllegalArgumentException("\"" + method + "\" is not a default method");
		}
		@SuppressWarnings("unchecked")
		Class<? extends Proxy> proxyClass = (Class<? extends Proxy>) proxy.getClass();

		// access check on the default method

		MethodHandle mh = defaultMethodHandle(proxyClass, method);
		// invoke the super method
		try {
			// the args array can be null if the number of formal parameters required by
			// the method is zero (consistent with Method::invoke)
			Object[] params = args != null ? args : EMPTY_ARGS;
			return mh.invokeExact(proxy, params);
		} catch (ClassCastException | NullPointerException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
