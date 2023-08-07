package com.raos.autocode.core.beans;

import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Bindable;
import com.raos.autocode.core.annotation.beans.Immutable;
import com.raos.autocode.core.annotation.beans.Observable;
import com.raos.autocode.core.annotation.beans.ObserverChangeClass;
import com.raos.autocode.core.annotation.beans.ObserverChangeMethod;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
import com.raos.autocode.core.beans.property.impl.BindablePropertyImpl;
import com.raos.autocode.core.beans.property.impl.ImmutablePropertyImpl;
import com.raos.autocode.core.beans.property.impl.ObservablePropertyImpl;
import com.raos.autocode.core.beans.property.impl.PropertyImpl;
import com.raos.autocode.core.util.ExceptionUtil;
import com.raos.autocode.core.util.ExceptionUtil.TFunction;

public final class BeanFactory implements InvocationHandler {
	// Object override methods
	public static final Method HASH_CODE;
	public static final Method EQUALS;
	public static final Method TO_STRING;
	public static final Method GET_PROPERTY;
	public static final Method GET_PROPERTIES;

	// Object map
	private Map<Object, BeanDelegate> proxies;

	public BeanFactory() {
		// Create a new concurrent hashmap for thread safety
		proxies = new ConcurrentHashMap<>();
	}

	// Create a bean via proxy
	@SuppressWarnings("unchecked")
	public <T> T createBean(Class<T> beanClass) {
		// Create the proxy instance
		Object value = Proxy.newProxyInstance(beanClass.getClassLoader(),
				new Class[] { beanClass, PropertyManager.class, Serializable.class }, this);
		// Put proxy bean delegate in map
		proxies.put(value, new BeanDelegate(value, beanClass));
		// Return value
		return (T) value;
	}

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

	// Bean delegate
	private class BeanDelegate {
		// Object wrapping
		private Object bean;
		private Class<?> beanClass;
		private SortedSet<Property<?>> properties;
		private Map<Class<?>, PropertyChangeFilter<?>> filterClasses;
		private Map<Class<?>, PropertyChangeListener<?>> listenerClasses;

		// Constructor
		private BeanDelegate(Object bean, Class<?> beanClass) {
			this.bean = bean;
			this.beanClass = beanClass;

			filterClasses = new HashMap<>();
			listenerClasses = new HashMap<>();

			// Create a properties tree set
			properties = new TreeSet<>(Comparator.comparing(Property::getName));

			// Stream the properties
			// Make sure to get only the properties that are
			// - abstract methods
			// - has Property as return type
			// - has no parameters
			// - and has the annotation @BeanProperty assigned
			Arrays.stream(beanClass.getMethods()).filter(Predicate.not(Method::isDefault))
					.filter(m -> m.getReturnType() == Property.class).filter(m -> m.getParameterCount() == 0)
					.filter(m -> m.isAnnotationPresent(BeanProperty.class))
					.map(ExceptionUtil.<Method, Property<?>>throwSilently(this::createProperty))
					.forEach(properties::add);

			properties = Collections.unmodifiableSortedSet(properties);
		}

		@SuppressWarnings("unchecked")
		public <T> Property<T> createProperty(Method m) throws Throwable {
			// Get the annotation
			BeanProperty annotation = m.getAnnotation(BeanProperty.class);

			// Get the class and nullable
			Class<T> propertyType = (Class<T>) annotation.type();
			boolean nullable = annotation.nullable();

			// Check if this is immutable
			if (m.isAnnotationPresent(Immutable.class)) {
				// Create a new immutable property
				return new ImmutablePropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable);

				// Check if this is observable
			} else if (m.isAnnotationPresent(Observable.class) || m.isAnnotationPresent(Bindable.class)) {

				// Create a new observable property
				ObservableProperty<T> property;

				// If its bindable then create a bindable property
				if (m.isAnnotationPresent(Bindable.class))
					property = new BindablePropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable);
				else
					property = new ObservablePropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType,
							nullable);

				if (m.isAnnotationPresent(ObserverChangeMethod.class)) {
					// Allowed listeners: Runnable, Consumer<Property<?>>, TriConsumer<Property<?>, ?, ?>
					String methodName = m.getAnnotation(ObserverChangeMethod.class).methodName();
					
					//
					
//					LambdaMetafactory.metafactory(MethodHandles.privateLookupIn(propertyType, beanClass), methodName, null, null, null, null);
					
					
				}

				if (m.isAnnotationPresent(ObserverChangeClass.class)) {

					// Get the listener class from the annotation
					Class<? extends PropertyChangeListener<?>> listenerClass = m
							.getAnnotation(ObserverChangeClass.class).listenerClass();

					// Put if absent new listener class
					listenerClasses.putIfAbsent(listenerClass, listenerClass.getDeclaredConstructor().newInstance());

					// Get the listener class
					property.getListeners().add((PropertyChangeListener<T>) listenerClasses.get(listenerClass));

				}

				return property;

				// Check if this is Bindable
			} else {
				return new PropertyImpl<>(m.getName(), (PropertyManager) bean, propertyType, nullable);
			}
		}

		// Invocation method
		public Object invoke(Method method, Object[] args) throws Throwable {
			// Object class methods
			if (method.equals(HASH_CODE)) {
				return PropertyManager.hashCode((PropertyManager) bean);
			}

			if (method.equals(EQUALS)) {
				if (!(args[1] instanceof PropertyManager))
					return false;
				
				return PropertyManager.equals((PropertyManager) bean, (PropertyManager) args[1]);
			}

			if (method.equals(TO_STRING)) {
				return PropertyManager.toString((PropertyManager) bean);
			}

			// Property Manager methods
			if (method.equals(GET_PROPERTIES)) {
				return null;
			}

			if (method.equals(GET_PROPERTY)) {

			}

			// Property Methods

			// ADD MORE HANDLING
			return null;
		}
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// Handle only
		return proxies.get(proxy).invoke(method, args);

	}
}
