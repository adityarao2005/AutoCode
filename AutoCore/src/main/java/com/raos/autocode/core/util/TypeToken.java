package com.raos.autocode.core.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Type token, used to capture generic types during runtime
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public abstract class TypeToken<T> {
	private Type genericType;

	public TypeToken() {
		this.genericType = getSuperclassTypeParameter(this.getClass());
	}

	private static Type getSuperclassTypeParameter(Class<?> subclass) {
		Type superclass = subclass.getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		ParameterizedType parameterized = (ParameterizedType) superclass;
		return parameterized.getActualTypeArguments()[0];
	}

	public Type getType() {
		return genericType;
	}

}
