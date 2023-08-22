package com.raos.autocode.core.beans;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.raos.autocode.core.annotation.beans.observable.Observable;
import com.raos.autocode.core.annotation.beans.observable.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.observable.ObserverFilterMethod;
import com.raos.autocode.core.annotation.beans.observable.ObserverListenerClass;
import com.raos.autocode.core.annotation.beans.observable.ObserverListenerMethod;
import com.raos.autocode.core.annotation.beans.property.BeanProperty;
import com.raos.autocode.core.annotation.beans.property.ReadOnly;
import com.raos.autocode.core.annotations.ToDo;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.event.ErrorHandlingPropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
import com.raos.autocode.core.util.ExceptionUtil;
import com.raos.autocode.core.util.ProxyUtil;
import com.raos.autocode.core.util.ReflectionUtil;

// Bean delegate
final class BeanDelegate {
	// Object override methods
	public static final Method HASH_CODE;
	public static final Method EQUALS;
	public static final Method TO_STRING;
	public static final Method GET_PROPERTY;
	public static final Method GET_PROPERTIES;

	// Methods
	static {
		Class<Object> object = Object.class;
		Class<PropertyManager> manager = PropertyManager.class;

		try {
			HASH_CODE = object.getDeclaredMethod("hashCode");
			EQUALS = object.getDeclaredMethod("equals", object);
			TO_STRING = object.getDeclaredMethod("toString");
			GET_PROPERTY = manager.getDeclaredMethod("getProperty", String.class);
			GET_PROPERTIES = manager.getDeclaredMethod("getProperties");
		} catch (NoSuchMethodException e) {
			// Never happens.
			throw new Error(e);
		}
	}

	// Object wrapping
	private Object bean;
	private BeanClass beanClass;
	private Map<String, Object> initParams;
	// Sorted Set
	private Map<String, Property<?>> properties;
	// Filters and Listeners
	private boolean isDestroyed = false;

	// Constructor
	public BeanDelegate(Object bean, BeanClass beanClass, Map<String, Object> initParams) {
		this.bean = bean;
		this.beanClass = beanClass;

		// Create a properties tree set
		properties = new HashMap<>();

		// Stream the properties
		// Make sure to get only the properties that are
		// - abstract methods
		// - has Property as return type
		// - has no parameters
		// - and has the annotation @BeanProperty assigned
		beanClass.getPropertyMethods().stream()
				.map(ExceptionUtil.<Method, Property<?>>throwSilently(method -> createProperty(method)))
				.forEach(p -> properties.put(p.getName(), p));

		// Add the properties
		properties = Collections.unmodifiableMap(properties);

		this.initParams = initParams;

	}

	void init() {

		// Check if the annotation is present
		for (String var : initParams.keySet()) {
			properties.get(var).setValue(initParams.get(var));
		}

		// Initialize the bean object
		Optional.ofNullable(beanClass.getInitMethod()).stream().map(ReflectionUtil.getAccessibleSetterOperator())
				.forEach(ExceptionUtil.<Method>throwSilently(m -> m.invoke(bean)));
	}

	void destroy() {
		if (isDestroyed)
			return;

		// Initialize the bean object
		Optional.ofNullable(beanClass.getDestroyMethod()).stream().map(ReflectionUtil.getAccessibleSetterOperator())
				.forEach(ExceptionUtil.<Method>throwSilently(m -> m.invoke(bean)));

		isDestroyed = true;
	}

	@ToDo(description = "add Decorator design pattern handling via @DecoratorClass and @DecoratorMethod")
	@SuppressWarnings({ "unchecked" })
	private <T> Property<T> createProperty(Method m) throws Throwable {
		// Get the annotation
		BeanProperty annotation = m.getAnnotation(BeanProperty.class);

		// Get the class and nullable
		Class<T> propertyType = (Class<T>) annotation.type();
		boolean nullable = annotation.nullable();

		boolean readOnly = m.isAnnotationPresent(ReadOnly.class);

		// Check if this is observable
		if (m.isAnnotationPresent(Observable.class)) {

			// Create a new observable property
			ObservableProperty<T> property = new ObservablePropertyImpl<>(m.getName(), (PropertyManager) bean,
					propertyType, nullable, readOnly);

			initObservableProperty(m, property);

			return property;

		} else {
			return new PropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable, readOnly);
		}
	}

	// Initialize an observable property
	@SuppressWarnings("unchecked")
	private <T> void initObservableProperty(Method m, ObservableProperty<T> property) throws Throwable {
		if (m.isAnnotationPresent(ObserverListenerMethod.class)) {
			// Allowed listeners: PropertyChangeListener<?>
			String methodName = m.getAnnotation(ObserverListenerMethod.class).methodName();

			// Get the methods which are acceptable
			Optional<Method> method = Optional.ofNullable(beanClass.getListenerMethods().get(methodName));

			try {
				property.getListeners().add(PropertyChangeListener.fromVirtual(bean, method.get()));
			} catch (Exception exception) {
				throw new BeansInstantationException("Unable to add listener method to properties", exception);
			}

		}

		if (m.isAnnotationPresent(ObserverListenerClass.class)) {

			// Get the listener class from the annotation
			Class<? extends PropertyChangeListener<?>> listenerClass = m.getAnnotation(ObserverListenerClass.class)
					.listenerClass();

			// Get the listener class
			property.getListeners().add((PropertyChangeListener<T>) beanClass.getFilterObjects().get(listenerClass));

		}

		if (m.isAnnotationPresent(ObserverFilterMethod.class)) {
			// Allowed listeners: PropertyChangeFilter<?>
			ObserverFilterMethod mAnnotation = m.getAnnotation(ObserverFilterMethod.class);
			String methodName = mAnnotation.methodName();

			// Get the methods which are acceptable

			Optional<Method> method = Optional.ofNullable(beanClass.getFilterMethods().get(methodName));

			try {
				property.getFilters().add(new ErrorHandlingPropertyChangeFilter<>(
						PropertyChangeFilter.fromVirtual(bean, method.get()), mAnnotation.errorMessage()));
			} catch (Exception exception) {
				throw new BeansInstantationException("Unable to add filter method to properties", exception);
			}

		}

		if (m.isAnnotationPresent(ObserverFilterClass.class)) {

			// Get the listener class from the annotation
			Class<? extends PropertyChangeFilter<?>> filterClass = m.getAnnotation(ObserverFilterClass.class)
					.filterClass();

			// Get the listener class
			property.getFilters().add((PropertyChangeFilter<T>) beanClass.getFilterObjects().get(filterClass));

		}
	}

	// Invocation method
	public Object invoke(Method method, Object[] args) throws Throwable {

		// Check destroyed
		if (isDestroyed)
			throw new NullPointerException("Bean is destroyed");

		// Object class methods
		if (method.equals(HASH_CODE)) {
			return PropertyManager.hashCode((PropertyManager) bean);
		}

		// Equals method
		if (method.equals(EQUALS)) {
			if (!(args[1] instanceof PropertyManager))
				return false;

			return PropertyManager.equals((PropertyManager) bean, (PropertyManager) args[1]);
		}

		// To string method
		if (method.equals(TO_STRING)) {
			return String.format("%s [ %s ]", beanClass.getBeanClass(), properties.values().stream()
					.map(p -> String.format("%s = %s", p.getName(), p.get())).collect(Collectors.joining(", ")));
		}

		// Property Manager methods
		if (method.equals(GET_PROPERTIES)) {
			return new HashSet<>(properties.values());
		}

		// Get property or else return null
		if (method.equals(GET_PROPERTY)) {
			return properties.get(args[0]);
		}

		// Property Methods
		if (BeanClass.isPropertyMethod(method)) {
			return properties.get(method.getName());
		}

		// default methods
		if (method.isDefault())
			return ProxyUtil.invokeDefault(bean, method, args);

		// ADD MORE HANDLING
		return null;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

}
