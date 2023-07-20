package com.raos.autocode.core.beans.property;

@FunctionalInterface
public interface WritableProperty<T> {

	void set(T value);
}
