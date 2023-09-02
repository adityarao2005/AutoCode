package com.raos.autocode.core.beans.property;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Acts as a property that is writable
 * @author Raos
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
@FunctionalInterface
public interface WritableProperty<T> {

	/**
	 * Sets the value of the property
	 * 
	 * @param value
	 */
	void set(Object value);
}
