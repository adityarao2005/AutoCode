package com.raos.autocode.core.beans;

import java.util.Map;

import com.raos.autocode.core.annotations.ToDo;

// Uses dynamic proxies to create beans
// ReadOnlyProperties - using stack trace elements to allow write methods
@ToDo(description = "Add a new class called BeanManager which handles the lifecycle of beans as well as contains a collection of all beans and their respective bean classes. Each BeanFactory will have their own BeanManager")
public final class BeanFactory implements AutoCloseable {
	// Closed
	private BeanManager manager;

	public BeanFactory() {
		// create bean manager
		manager = new BeanManager();
	}

	// Create a bean via proxy
	public <T> T createBean(Class<T> beanClass) throws BeansInstantationException {
		return createBean(beanClass, Map.of());
	}

	// Create a bean via proxy
	public <T> T createBean(Class<T> beanClass, Map<String, Object> map) throws BeansInstantationException {
		return manager.createBean(beanClass, map);

	}

	// Destroy a bean
	public void destroyBean(Object bean) {
		manager.destroyBean(bean);
	}

	// Checks whether bean has been destroyed
	public boolean checkDestroyed(Object bean) {
		return manager.checkDestroyed(bean);
	}

	@Override
	public void close() {
		manager.close();
	}

}
