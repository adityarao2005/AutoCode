package com.raos.autocode.core.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.PropertyManager;

@ClassPreamble(author = "Aditya Rao", date = "8/15/2023")
class BeanManager implements InvocationHandler, AutoCloseable {
	// Closed
	private boolean closed;
	// Proxies
	private Map<Integer, BeanDelegate> proxies;
	// Bean Classes
	private Map<Class<?>, BeanClass> beanClasses;

	public BeanManager() {
		proxies = new ConcurrentHashMap<>();
		beanClasses = new ConcurrentHashMap<>();
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
		getAtObject(bean).destroy();

	}

	// Checks whether bean has been destroyed
	public boolean checkDestroyed(Object bean) {
		checkClosed();

		// Checks whether bean has been destroyed
		return getAtObject(bean).isDestroyed();
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		checkClosed();

		// Handle only
		return getAtObject(proxy).invoke(method, args);

	}

	// Get at the object
	public BeanDelegate getAtObject(Object proxy) {
		return proxies.get(System.identityHashCode(proxy));
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
			proxies.values().forEach(BeanDelegate::destroy);
			proxies.clear();
			proxies = null;

			// clear the bean classes
			beanClasses.clear();
			beanClasses = null;

			closed = true;
		}
	}

	// Bean Classes
	public Map<Integer, BeanDelegate> getProxies() {
		return proxies;
	}

	public Map<Class<?>, BeanClass> getBeanClasses() {
		return beanClasses;
	}

}
