package com.raos.autocode.core.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Utility class for method handles
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface MethodHandleUtils {

	/**
	 * Converts method handle to functional interface
	 * 
	 * @param <T>
	 * @param lambdaClass
	 * @param methodHandle
	 * @return
	 */
	public static <T> T toLambda(Class<T> lambdaClass, MethodHandle methodHandle) {
		return MethodHandleProxies.asInterfaceInstance(lambdaClass, methodHandle);
	}

	/**
	 * Gets methodhandle from method
	 * 
	 * @param method
	 * @param privateAccess
	 * @return
	 * @throws IllegalAccessException
	 */
	public static MethodHandle fromMethod(Method method, boolean privateAccess) throws IllegalAccessException {
		return (privateAccess ? MethodHandles.privateLookupIn(method.getDeclaringClass(), MethodHandles.lookup())
				: MethodHandles.lookup()).unreflect(method);
	}

	/**
	 * Sets caller/owner object
	 * 
	 * @param methodHandle
	 * @param obj
	 * @return
	 */
	public static MethodHandle setCallerObject(MethodHandle methodHandle, Object obj) {
		return methodHandle.bindTo(obj);
	}

	/**
	 * Converts method to method type
	 */
	public static MethodType toMethodType(Method method) {
		return MethodType.methodType(method.getReturnType(), method.getParameterTypes());
	}
}
