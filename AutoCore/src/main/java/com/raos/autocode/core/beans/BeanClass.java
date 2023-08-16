package com.raos.autocode.core.beans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Destructor;
import com.raos.autocode.core.annotation.beans.Init;
import com.raos.autocode.core.annotation.beans.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.ObserverFilterMethod;
import com.raos.autocode.core.annotation.beans.ObserverListenerClass;
import com.raos.autocode.core.annotation.beans.ObserverListenerMethod;
import com.raos.autocode.core.annotations.ToDo;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
import com.raos.autocode.core.util.ExceptionUtil;
import com.raos.autocode.core.util.ExceptionUtil.TConsumer;
import com.raos.autocode.core.util.ReflectionUtil;

@ToDo(description = "work on creating a property creation registry for the bean delegate")
final class BeanClass {
	// Object map
	private Class<?> beanClass;

	// Bean details
	private List<Method> propertyMethods;
	private Map<Class<? extends PropertyChangeFilter<?>>, PropertyChangeFilter<?>> filterObjects;
	private Map<Class<? extends PropertyChangeListener<?>>, PropertyChangeListener<?>> listenerObjects;
	private Map<String, Method> filterMethods;
	private Map<String, Method> listenerMethods;
	private Method initMethod;
	private Method destroyMethod;

	private Map<Class<? extends Annotation>, TConsumer<? extends Annotation>> annotationRegistry = new HashMap<>();

	public BeanClass(Class<?> beanClass) {
		// Create a new concurrent hashmap for thread safety
		this.beanClass = beanClass;

		// spy
		ExceptionUtil.throwSilently(this::spy).run();

		register(ObserverListenerClass.class, this::handleObserverListenerClassAnnotation);
		register(ObserverListenerMethod.class, this::handleObserverListenerMethodAnnotation);
		register(ObserverFilterClass.class, this::handleObserverFilterClassAnnotation);
		register(ObserverFilterMethod.class, this::handleObserverFilterMethodAnnotation);
	}

	// Registry
	private <T extends Annotation> void register(Class<T> clazz, TConsumer<T> consumer) {
		annotationRegistry.put(clazz, consumer);
	}

	@SuppressWarnings("unchecked")
	private <T extends Annotation> void call(Class<T> clazz, Method method) throws Throwable {
		T annotation = method.getAnnotation(clazz);
		((TConsumer<T>) annotationRegistry.get(clazz)).accept(annotation);
	}

	// Spy on the class and decode all attributes
	private void spy() throws Throwable {
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
		for (Method method : propertyMethods)
			for (Class<? extends Annotation> annotationClass : annotationRegistry.keySet())
				if (method.isAnnotationPresent(annotationClass))
					call(annotationClass, method);

		// Get init method
		initMethod = ReflectionUtil.getMethodsWithAnnotation(beanClass, true, Init.class).findAny().orElse(null);
		destroyMethod = ReflectionUtil.getMethodsWithAnnotation(beanClass, true, Destructor.class).findAny()
				.orElse(null);
	}

	public static boolean isPropertyMethod(Method m) {
		return !m.isDefault() && Property.class.isAssignableFrom(m.getReturnType()) && m.getParameterCount() == 0;
	}

	public Object construct(BeanManager factory, Map<String, Object> initParams) {
		// Create the proxy instance
		Object proxy = Proxy.newProxyInstance(beanClass.getClassLoader(),
				new Class[] { beanClass, PropertyManager.class }, factory);

		// Put proxy bean delegate in map
		BeanDelegate delegate = new BeanDelegate(proxy, this, initParams);

		// Add this to the proxies
		factory.getProxies().put(System.identityHashCode(proxy), delegate);

		// Initialize the delegate
		delegate.init();

		// Return value
		return proxy;
	}

	public Class<?> getBeanClass() {
		return beanClass;
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

	@SuppressWarnings("unchecked")
	public void handleObserverListenerClassAnnotation(ObserverListenerClass annotation) throws Exception {
		// Get the listener class from annotation
		Class<? extends PropertyChangeListener<?>> changeListener = annotation.listenerClass();

		PropertyChangeListener<?> listener;
		// Create a new instance and add
		listenerObjects.putIfAbsent(changeListener, listener = changeListener.getConstructor().newInstance());

		// Check whether property
		if (listener instanceof PropertyChangeFilter) {
			filterObjects.putIfAbsent((Class<? extends PropertyChangeFilter<?>>) listener.getClass(),
					(PropertyChangeFilter<?>) listener);
		}
	}

	@SuppressWarnings("unchecked")
	public void handleObserverFilterClassAnnotation(ObserverFilterClass annotation) throws Exception {
		// Get the listener class from annotation
		Class<? extends PropertyChangeFilter<?>> changeFilter = annotation.filterClass();

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
	public void handleObserverFilterMethodAnnotation(ObserverFilterMethod annotation) throws Exception {
		// Get method name
		String methodName = annotation.methodName();

		// Get the method
		filterMethods.put(methodName, ReflectionUtil.getMethodByName(methodName, beanClass, false).get());
	}

	// If the method has ObserverFilterMethod annotation
	public void handleObserverListenerMethodAnnotation(ObserverListenerMethod annotation) throws Exception {
		// Get method name
		String methodName = annotation.methodName();

		// Get the method
		listenerMethods.put(methodName, ReflectionUtil.getMethodByName(methodName, beanClass, false).get());
	}
}
