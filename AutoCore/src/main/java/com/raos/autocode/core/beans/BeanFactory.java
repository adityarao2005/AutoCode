package com.raos.autocode.core.beans;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.raos.autocode.core.beans.property.PropertyManager;

public final class BeanFactory implements InvocationHandler {

	// Object map
	private Map<Integer, BeanDelegate> proxies;

	public BeanFactory() {
		// Create a new concurrent hashmap for thread safety
		proxies = new ConcurrentHashMap<>();
	}

	// Create a bean via proxy
	public <T> T createBean(Class<T> beanClass) {
		return createBean(beanClass, Map.of());
	}

	// Create a bean via proxy
	@SuppressWarnings("unchecked")
	public <T> T createBean(Class<T> beanClass, Map<String, Object> map) {
		// Create the proxy instance
		Object value = Proxy.newProxyInstance(beanClass.getClassLoader(),
				new Class[] { beanClass, PropertyManager.class }, this);
		// Put proxy bean delegate in map
		BeanDelegate delegate = new BeanDelegate(value, beanClass, map);

		proxies.put(System.identityHashCode(value), delegate);

		delegate.init();
		// Return value
		return (T) value;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		// Handle only
		return proxies.get(System.identityHashCode(proxy)).invoke(method, args);

	}
}
