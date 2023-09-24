package com.raos.autocode.core.design.builder;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents a generic builder
 * 
 * @author Raos
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface GenericBuilder<T> extends Builder<T> {

	@Override
	public default T build() {
		// Create the value
		T value = newInstance();

		// Apply the value
		apply(value);

		// Return the value
		return value;
	}

	/**
	 * Creates a new instance
	 * 
	 * @return
	 */
	T newInstance();

	/**
	 * Applies builder properties onto object
	 * 
	 * @param object
	 */
	void apply(T object);

	/**
	 * Sets property and returns this builder
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	GenericBuilder<T> setProperty(String propertyName, Object value);

}
