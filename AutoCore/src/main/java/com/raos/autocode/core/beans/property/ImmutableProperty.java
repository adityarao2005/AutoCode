package com.raos.autocode.core.beans.property;

public interface ImmutableProperty<T> extends Property<T> {

	@Override
	public default void set(T value) {
		// TODO: Do nothing for now, maybe later add a mechanism to error check
	}
}
