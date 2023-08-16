package com.raos.autocode.core.beans.property.event;

import java.lang.reflect.Method;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.util.ExceptionUtil;
import com.raos.autocode.core.util.MethodHandleUtils;

public interface PropertyChangeListener<T> {
	// On event action
	public void onChange(Property<T> property, T old, T newv);

	@SuppressWarnings("unchecked")
	public static <T> PropertyChangeListener<T> fromVirtual(Object annotated, Method method) {

		return ExceptionUtil.throwSilently(() -> MethodHandleUtils.toLambda(PropertyChangeListener.class,
				MethodHandleUtils.fromMethod(method, true).bindTo(annotated))).get();

	}
}
