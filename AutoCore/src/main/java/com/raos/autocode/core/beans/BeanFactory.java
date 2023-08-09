package com.raos.autocode.core.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.raos.autocode.core.beans.property.PropertyManager;

public final class BeanFactory implements InvocationHandler {

	// Object map
	private Map<Class<?>, BeanClass> beanClasses;

	public BeanFactory() {
		// Create a new concurrent hashmap for thread safety
		beanClasses = new ConcurrentHashMap<>();
	}

	// Create a bean via proxy
	public <T> T createBean(Class<T> beanClass) {
		return createBean(beanClass, Map.of());
	}

	// Create a bean via proxy
	@SuppressWarnings({ "unchecked", "deprecation" })
	public <T> T createBean(Class<T> beanClass, Map<String, Object> map) {
		Class<?> proxyClass = Proxy.getProxyClass(beanClass.getClassLoader(), new Class[] { beanClass, PropertyManager.class });

		// Make sure we have the proxy class in our map
		beanClasses.putIfAbsent(proxyClass, new BeanClass(beanClass));

		// Put proxy bean delegate in map
		return (T) beanClasses.get(proxyClass).construct(this, map);

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// Handle only
		return beanClasses.get(proxy.getClass()).getAtObject(proxy).invoke(method, args);

	}
}
