package com.raos.autocode.core.beans.property;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents a generic property
 * 
 * @author Raos
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
public interface Property<T> extends Pointer<T> {

	/**
	 * Retrieves the name of the property
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retrieves the owner/bean associated with the property
	 * 
	 * @return
	 */
	PropertyManager getBean();

	/**
	 * Gets the type of the property
	 * 
	 * @return
	 */
	Class<T> getType();

	/**
	 * Default methods to access value
	 * 
	 * @return
	 */
	public default T getValue() {
		return get();
	}

	/**
	 * Default methods to mutate value
	 * 
	 * @return
	 */
	public default void setValue(Object value) {
		set(value);
	}
}
