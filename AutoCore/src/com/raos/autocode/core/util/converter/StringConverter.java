package com.raos.autocode.core.util.converter;

import com.raos.autocode.core.annotations.ToDo;

// Enables the transformation of objects from and to string values
@ToDo(description = "Add multiple converters from and to string", methods = "")
public abstract class StringConverter<T> {

	public abstract T fromString(String string);
	
	public abstract String toString(T t);
}
