package com.raos.autocode.core.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.raos.autocode.core.annotations.ToDo;
import com.raos.autocode.core.beans.property.PropertyManager;

// Uses dynamic proxies to create beans
// ReadOnlyProperties - using stack trace elements to allow write methods
@ToDo(description = "Add a new class called BeanManager which handles the lifecycle of beans as well as contains a collection of all beans and their respective bean classes. Each BeanFactory will have their own BeanManager")
public final class BeanFactory implements InvocationHandler, AutoCloseable {
	// Closed
	private boolean closed;

	// Object map
	private Map<Class<?>, BeanClass> beanClasses;

	public BeanFactory() {
		
		// Create a new concurrent hashmap for thread safety
		beanClasses = new ConcurrentHashMap<>();
	}

	// Create a bean via proxy
	public <T> T createBean(Class<T> beanClass) throws BeansInstantationException {
		return createBean(beanClass, Map.of());
	}

	// Create a bean via proxy
	@SuppressWarnings({ "unchecked" })
	public <T> T createBean(Class<T> beanClass, Map<String, Object> map) throws BeansInstantationException {
		checkClosed();

		// Return a proxy class
		Class<?> proxyClass = registerBeanClass(beanClass);

		// Put proxy bean delegate in map
		return (T) beanClasses.get(proxyClass).construct(this, map);

	}

	// registers the bean class and returns a proxy class
	@SuppressWarnings("deprecation")
	public Class<?> registerBeanClass(Class<?> beanClass) {
		Class<?> proxyClass = Proxy.getProxyClass(beanClass.getClassLoader(),
				new Class[] { beanClass, PropertyManager.class });

		// Make sure we have the proxy class in our map
		beanClasses.putIfAbsent(proxyClass, new BeanClass(beanClass));

		return proxyClass;
	}

	// Destroy a bean
	public void destroyBean(Object bean) {
		checkClosed();

		// Check destroyed
		if (checkDestroyed(bean))
			return;

		// Destroy the bean
		beanClasses.get(bean.getClass()).getAtObject(bean).destroy();

	}

	// Checks whether bean has been destroyed
	public boolean checkDestroyed(Object bean) {
		checkClosed();

		// Checks whether bean has been destroyed
		return beanClasses.get(bean.getClass()).getAtObject(bean).isDestroyed();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		checkClosed();

		// Handle only
		return beanClasses.get(proxy.getClass()).getAtObject(proxy).invoke(method, args);

	}

	private void checkClosed() {
		if (closed)
			throw new IllegalStateException("BeanFactory is closed");
	}

	@Override
	public void close() {
		// Make sure closed
		if (!closed) {

			// Invoke the destroy method
			beanClasses.values().stream().map(BeanClass::getProxies).map(Map::values).flatMap(Collection::stream)
					.forEach(BeanDelegate::destroy);

			// Clear the proxies map
			beanClasses.values().stream().map(BeanClass::getProxies).forEach(Map::clear);

			// clear the bean classes
			beanClasses.clear();

			// set bean classes to null
			beanClasses = null;

			closed = true;
		}
	}
}
