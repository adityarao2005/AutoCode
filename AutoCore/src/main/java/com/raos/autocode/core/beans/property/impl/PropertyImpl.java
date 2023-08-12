package com.raos.autocode.core.beans.property.impl;

import com.raos.autocode.core.beans.property.PropertyManager;

public class PropertyImpl<T> extends AbstractProperty<T> {

	public PropertyImpl() {
		super();
	}

	public PropertyImpl(String name, PropertyManager bean, Class<T> type, boolean nullable, boolean readOnly, T value) {
		super(name, bean, type, nullable, readOnly, value);
	}

	public PropertyImpl(String name, PropertyManager bean, Class<T> type, boolean nullable, boolean readOnly) {
		super(name, bean, type, nullable, readOnly);
	}

}
