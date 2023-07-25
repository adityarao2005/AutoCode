package com.raos.autocode.core.design.builder;

public interface GenericBuilder<T> extends Builder<T> {

	public default T build() {
		// Create the value
		T value = newInstance();

		// Apply the value
		apply(value);

		// Return the value
		return value;
	}

	// Creates a new instance
	T newInstance();

	// Applies builder properties onto object
	void apply(T object);

	// Sets property and returns this
	GenericBuilder<T> setProperty(String propertyName, Object value);

}
