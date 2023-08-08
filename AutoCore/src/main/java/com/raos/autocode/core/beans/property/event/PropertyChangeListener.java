package com.raos.autocode.core.beans.property.event;

import java.lang.reflect.Method;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.util.ExceptionUtil;

public interface PropertyChangeListener<T> {
	// On event action
	public void onChange(Property<T> property, T old, T newv);

	public static <T> PropertyChangeListener<T> fromVirtual(Object annotated, Method method) {

		return ExceptionUtil.throwSilently((a, v, c) -> method.invoke(annotated, a, v, c))::accept;

	}
}
