package com.raos.autocode.core.design.builder;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract implementation of Builder
 * @author Raos
 *
 * @param <T>
 */
public abstract class AbstractBuilder<T> implements GenericBuilder<T> {
	// Properties
	protected Map<String, Object> properties;

	// Protected Constructor
	protected AbstractBuilder() {
		properties = new HashMap<>();
	}

	// Set the property and return the current builder
	public AbstractBuilder<T> setProperty(String key, Object value) {
		properties.put(key, value);

		return this;
	}

	// Copy from another builder
	public AbstractBuilder<T> copy(AbstractBuilder<T> other) {
		properties.putAll(other.properties);

		return this;
	}

	// Create a new instance
	@Override
	public void apply(T value) {

		// Add its properties
		for (String fieldName : properties.keySet()) {
			// Set the value of the object
			try {
				// Invoke setter
				new PropertyDescriptor(fieldName, value.getClass()).getWriteMethod().invoke(value,
						properties.get(value));
			} catch (Exception e) {

				// If there is no getter, resort to changing the field directly
				try {
					Field field = value.getClass().getDeclaredField(fieldName);
					field.setAccessible(true);
					field.set(value, properties.get(fieldName));
				} catch (Exception e1) {
					// throw exception
					throw new RuntimeException(String.format("Unable to access property: %s", fieldName));
				}

			}
		}

	}

	// Get from prototype
	public AbstractBuilder<T> from(T prototype) {
		// Clear properties
		properties.clear();

		try {
			// For each property
			for (PropertyDescriptor descriptor : Introspector.getBeanInfo(prototype.getClass())
					.getPropertyDescriptors()) {
				// Get the value
				Object value = descriptor.getReadMethod().invoke(prototype);
				// If the value is not null, then keep in properties
				if (value != null)
					properties.put(descriptor.getName(), value);
			}
		} catch (Exception e) {
			throw new RuntimeException("Cannot get values from prototype");
		}

		// Return this
		return this;
	}
}
