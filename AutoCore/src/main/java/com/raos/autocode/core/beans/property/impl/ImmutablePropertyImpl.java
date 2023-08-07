package com.raos.autocode.core.beans.property.impl;

import com.raos.autocode.core.beans.property.ImmutableProperty;
import com.raos.autocode.core.beans.property.PropertyManager;

@Deprecated
public class ImmutablePropertyImpl<T> extends AbstractProperty<T> implements ImmutableProperty<T> {

	public ImmutablePropertyImpl() {
	}

	public ImmutablePropertyImpl(String name, PropertyManager bean, Class<T> type, boolean nullable) {
		super(name, bean, type, nullable);
	}

	public ImmutablePropertyImpl(String name, PropertyManager bean, Class<T> type, boolean nullable, T value) {
		super(name, bean, type, nullable, value);
	}

	@Override
	public void set(Object value) {
		// Set through immutable property interfaces
		ImmutableProperty.super.set(value);
	}

}
