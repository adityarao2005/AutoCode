package com.raos.autocode.core.beans;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.JavaBeanPropertyWrapper.PropertyDescriptorAccessor;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.util.ExceptionUtil;

/**
 * A property manager which wraps a java bean
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "8/30/2023")
public class JavaBeanPropertyManager implements PropertyManager {
	// Store the properties
	private Map<String, Property<?>> properties;

	/**
	 * Creates an instance of the class which taking in a java bean
	 * @param instance
	 */
	public JavaBeanPropertyManager(Object instance) {
		// Properties
		this.properties = new HashMap<>();

		// Get the bean info
		BeanInfo info = ExceptionUtil.throwSilentlyAndGet(() -> Introspector.getBeanInfo(instance.getClass()));

		// Go throguh all the property descriptrs
		for (PropertyDescriptor descriptor : info.getPropertyDescriptors()) {
			if (descriptor.getName().equals("class"))
				continue;

			// Put in the properties
			properties.put(descriptor.getName(),
					new JavaBeanPropertyWrapper<>(new PropertyDescriptorAccessor<>(instance, descriptor), this));
		}

		// Create an immutable map
		properties = Collections.unmodifiableMap(properties);
	}

	/**
	 * Gets property by name
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> Property<T> getProperty(String name) {
		return (Property<T>) properties.get(name);
	}

	/**
	 * Gets a bunch of properties
	 */
	@Override
	public Map<String, Property<?>> getProperties() {
		return properties;
	}

}
