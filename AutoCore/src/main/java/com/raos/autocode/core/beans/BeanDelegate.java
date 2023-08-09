package com.raos.autocode.core.beans;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Bindable;
import com.raos.autocode.core.annotation.beans.Observable;
import com.raos.autocode.core.annotation.beans.ObserverListenerClass;
import com.raos.autocode.core.annotation.beans.ObserverListenerMethod;
import com.raos.autocode.core.annotations.ToDo;
import com.raos.autocode.core.annotation.beans.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.ObserverFilterMethod;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.event.ErrorHandlingPropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
import com.raos.autocode.core.beans.property.impl.BindablePropertyImpl;
import com.raos.autocode.core.beans.property.impl.ObservablePropertyImpl;
import com.raos.autocode.core.beans.property.impl.PropertyImpl;
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
	private Map<Class<?>, PropertyChangeFilter<?>> filterClasses;
	private Map<Class<?>, PropertyChangeListener<?>> listenerClasses;

	// Constructor
	public BeanDelegate(Object bean, BeanClass beanClass, Map<String, Object> initParams) {
		this.bean = bean;
		this.beanClass = beanClass;

		filterClasses = new HashMap<>();
		listenerClasses = new HashMap<>();

		// Create a properties tree set
		properties = new HashMap<>();

		// Stream the properties
		// Make sure to get only the properties that are
		// - abstract methods
		// - has Property as return type
		// - has no parameters
		// - and has the annotation @BeanProperty assigned
		beanClass.getPropertyMethods().stream()
				.map(ExceptionUtil.<Method, Property<?>>throwSilently(this::createProperty))
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

	@ToDo(description = "add Decorator design pattern handling via @DecoratorClass and @DecoratorMethod")
	@SuppressWarnings({ "unchecked" })
	public <T> Property<T> createProperty(Method m) throws Throwable {
		// Get the annotation
		BeanProperty annotation = m.getAnnotation(BeanProperty.class);

		// Get the class and nullable
		Class<T> propertyType = (Class<T>) annotation.type();
		boolean nullable = annotation.nullable();

		// Check if this is observable
		if (m.isAnnotationPresent(Observable.class) || m.isAnnotationPresent(Bindable.class)) {

			// Create a new observable property
			ObservableProperty<T> property;

			// If its bindable then create a bindable property
			if (m.isAnnotationPresent(Bindable.class))
				property = new BindablePropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable);
			else
				property = new ObservablePropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable);

			initObservableProperty(m, property);

			return property;

		} else {
			return new PropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable);
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

			// Put if absent new listener class
			listenerClasses.putIfAbsent(listenerClass, listenerClass.getDeclaredConstructor().newInstance());

			// Get the listener class
			property.getListeners().add((PropertyChangeListener<T>) listenerClasses.get(listenerClass));

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

			// Put if absent new listener class
			filterClasses.putIfAbsent(filterClass, filterClass.getDeclaredConstructor().newInstance());

			// Get the listener class
			property.getFilters().add((PropertyChangeFilter<T>) filterClasses.get(filterClass));

		}
	}

	// Invocation method
	public Object invoke(Method method, Object[] args) throws Throwable {
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
			return String.format("%s [ %s ]", beanClass, properties.values().stream()
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
			return ProxyUtil.handleDefaultMethod(bean, beanClass.getBeanClass(), method, args);

		// ADD MORE HANDLING
		return null;
	}
}
