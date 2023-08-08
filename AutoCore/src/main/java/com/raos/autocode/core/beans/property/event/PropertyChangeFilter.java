package com.raos.autocode.core.beans.property.event;

import java.lang.reflect.Method;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.util.ExceptionUtil;

@FunctionalInterface
public interface PropertyChangeFilter<T> {
	// Allowed filters
	public boolean filter(Property<T> property, T newv);

	// Do nothing as default implementation
	public default void onError(T invalidValue) {
	}

	public static <T> PropertyChangeFilter<T> fromVirtual(Object annotated, Method method) {

		return ExceptionUtil.throwSilently((a, v) -> (boolean) method.invoke(annotated, a, v))::test;
	}
}
