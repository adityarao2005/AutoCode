package com.raos.autocode.core.beans.property;

// Should go to beans project
public interface Property<T> extends Pointer<T> {

	// Gets the name of the property
	String getName();

	// Gets the owner of the property
	PropertyManager getBean();
	
	// Gets the type of the property
	Class<T> getType();

	// Default methods to access and mutate value
	public default T getValue() {
		return get();
	}
	
	public default void setValue(T value) {
		set(value);
	}
}
