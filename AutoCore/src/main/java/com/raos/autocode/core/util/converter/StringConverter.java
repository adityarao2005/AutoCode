package com.raos.autocode.core.util.converter;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.annotations.ToDo;

/**
 * Enables the transformation of objects from and to string values
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
@ToDo(description = "Add multiple converters from and to string")
public abstract class StringConverter<T> {

	/**
	 * Converts string to object
	 * @param string
	 * @return
	 */
	public abstract T fromString(String string);
	
	/**
	 * Converts object to string
	 * @param t
	 * @return
	 */
	public abstract String toString(T t);
}
