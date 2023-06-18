package com.raos.autocode.core.property;

@FunctionalInterface
public interface WritableProperty<T> {

	void set(T value);
}
