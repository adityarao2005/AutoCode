package com.raos.autocode.core.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Destructor;
import com.raos.autocode.core.annotation.beans.Init;
import com.raos.autocode.core.annotation.beans.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.ObserverFilterMethod;
import com.raos.autocode.core.annotation.beans.ObserverListenerClass;
import com.raos.autocode.core.annotation.beans.ObserverListenerMethod;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
import com.raos.autocode.core.util.ExceptionUtil;
import com.raos.autocode.core.util.ReflectionUtil;

final class BeanClass {
	// Object map
	private Map<Integer, BeanDelegate> proxies;
	private Class<?> beanClass;

	// Bean details
	private List<Method> propertyMethods;
	private Map<Class<? extends PropertyChangeFilter<?>>, PropertyChangeFilter<?>> filterObjects;
	private Map<Class<? extends PropertyChangeListener<?>>, PropertyChangeListener<?>> listenerObjects;
	private Map<String, Method> filterMethods;
	private Map<String, Method> listenerMethods;
	private Method initMethod;
	private Method destroyMethod;

	public BeanClass(Class<?> beanClass) {
		// Create a new concurrent hashmap for thread safety
		proxies = new ConcurrentHashMap<>();
		this.beanClass = beanClass;

		// spy
		ExceptionUtil.throwSilently(this::spy).run();
	}

	// Spy on the class and decode all attributes
	@SuppressWarnings("unchecked")
	private void spy() throws Exception {
		// Stream over methods of beanClass and get only the methods which are property
		// methods and have the annotation "BeanProperty" present and collect to list
		propertyMethods = ReflectionUtil.getMethodsWithAnnotation(beanClass, false, BeanProperty.class)
				.filter(BeanClass::isPropertyMethod).collect(Collectors.toUnmodifiableList());

		// Initialize the other collections
		filterObjects = new HashMap<>();
		listenerObjects = new HashMap<>();
		filterMethods = new HashMap<>();
		listenerMethods = new HashMap<>();

		// Go through all the methods
		for (Method method : propertyMethods) {

			// If the method has ObserverListeenrClass annotation
			if (method.isAnnotationPresent(ObserverListenerClass.class)) {
				// Get the listener class from annotation
				Class<? extends PropertyChangeListener<?>> changeListener = method
						.getAnnotation(ObserverListenerClass.class).listenerClass();

				PropertyChangeListener<?> listener;
				// Create a new instance and add
				listenerObjects.putIfAbsent(changeListener, listener = changeListener.getConstructor().newInstance());

				// Check whether property
				if (listener instanceof PropertyChangeFilter) {
					filterObjects.putIfAbsent((Class<? extends PropertyChangeFilter<?>>) listener.getClass(),
							(PropertyChangeFilter<?>) listener);
				}
			}

			// If the method has ObserverFilterClass annotation
			if (method.isAnnotationPresent(ObserverFilterClass.class)) {
				// Get the listener class from annotation
				Class<? extends PropertyChangeFilter<?>> changeFilter = method.getAnnotation(ObserverFilterClass.class)
						.filterClass();

				PropertyChangeFilter<?> filter;
				// Create a new instance and add
				filterObjects.putIfAbsent(changeFilter, filter = changeFilter.getConstructor().newInstance());

				// Check whether property
				if (filter instanceof PropertyChangeListener) {
					listenerObjects.putIfAbsent((Class<? extends PropertyChangeListener<?>>) filter.getClass(),
							(PropertyChangeListener<?>) filter);
				}
			}

			// If the method has ObserverFilterMethod annotation
			if (method.isAnnotationPresent(ObserverFilterMethod.class)) {
				// Get method name
				String methodName = method.getAnnotation(ObserverFilterMethod.class).methodName();

				// Get the method
				filterMethods.put(methodName, ReflectionUtil.getMethodByName(methodName, beanClass, false).get());
			}

			// If the method has ObserverFilterMethod annotation
			if (method.isAnnotationPresent(ObserverListenerMethod.class)) {
				// Get method name
				String methodName = method.getAnnotation(ObserverListenerMethod.class).methodName();

				// Get the method
				listenerMethods.put(methodName, ReflectionUtil.getMethodByName(methodName, beanClass, false).get());
			}
		}

		// Get init method
		initMethod = ReflectionUtil.getMethodsWithAnnotation(beanClass, true, Init.class).findAny().orElse(null);
		destroyMethod = ReflectionUtil.getMethodsWithAnnotation(beanClass, true, Destructor.class).findAny()
				.orElse(null);
	}

	public static boolean isPropertyMethod(Method m) {
		return !m.isDefault() && m.getReturnType() == Property.class && m.getParameterCount() == 0;
	}

	public Object construct(BeanFactory factory, Map<String, Object> initParams) {
		// Create the proxy instance
		Object proxy = Proxy.newProxyInstance(beanClass.getClassLoader(),
				new Class[] { beanClass, PropertyManager.class }, factory);

		// Put proxy bean delegate in map
		BeanDelegate delegate = new BeanDelegate(proxy, this, initParams);

		// Add this to the proxies
		proxies.put(System.identityHashCode(proxy), delegate);

		// Initialize the delegate
		delegate.init();

		// Return value
		return proxy;
	}

	// Get at the object
	public BeanDelegate getAtObject(Object proxy) {
		return proxies.get(System.identityHashCode(proxy));
	}

	public Class<?> getBeanClass() {
		return beanClass;
	}

	// Proxies
	public Map<Integer, BeanDelegate> getProxies() {
		return proxies;
	}

	public List<Method> getPropertyMethods() {
		return propertyMethods;
	}

	public Map<Class<? extends PropertyChangeFilter<?>>, PropertyChangeFilter<?>> getFilterObjects() {
		return filterObjects;
	}

	public Map<Class<? extends PropertyChangeListener<?>>, PropertyChangeListener<?>> getListenerObjects() {
		return listenerObjects;
	}

	public Map<String, Method> getFilterMethods() {
		return filterMethods;
	}

	public Map<String, Method> getListenerMethods() {
		return listenerMethods;
	}

	public Method getInitMethod() {
		return initMethod;
	}

	public Method getDestroyMethod() {
		return destroyMethod;
	}

}
