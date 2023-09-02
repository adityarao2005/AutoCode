package com.raos.autocode.core.beans.property;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Acts as a property that is readable
 * @author Raos
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
@FunctionalInterface
public interface ReadableProperty<T> {

	/**
	 * @return the value of the property
	 */
	T get();
}
