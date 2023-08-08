package com.raos.autocode.core.beans.property.impl;

import java.util.LinkedList;
import java.util.List;

import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

public class ObservablePropertyImpl<T> extends AbstractProperty<T> implements ObservableProperty<T> {
	private List<PropertyChangeListener<T>> listeners = new LinkedList<>();
	private List<PropertyChangeFilter<T>> filters = new LinkedList<>();

	// Constructors
	public ObservablePropertyImpl() {
	}

	public ObservablePropertyImpl(String name, PropertyManager bean, Class<T> type, boolean nullable, T value) {
		super(name, bean, type, nullable, value);
	}

	public ObservablePropertyImpl(String name, PropertyManager bean, Class<T> type, boolean nullable) {
		super(name, bean, type, nullable);
	}

	// Getters
	@Override
	public List<PropertyChangeListener<T>> getListeners() {
		return listeners;
	}

	@Override
	public List<PropertyChangeFilter<T>> getFilters() {
		return filters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(Object value) {
		// Null check
		if (value == null)
			if (!isNullable())
				throw new NullPointerException("Null values not allowed");
		// Type check
		else if (!getType().isInstance(value))
				throw new ClassCastException("The argument passed is not a valid type");

		// Check if we allow
		for (PropertyChangeFilter<T> filter : getFilters()) {
			if (!filter.filter(this, (T) value)) {
				// ON error
				filter.onError((T) value);
				return;
			}
		}

		// Get old value
		T old = this.get();
		// Set the value
		super.set(value);

		// Perform change
		for (PropertyChangeListener<T> listener : getListeners())
			listener.onChange(this, old, (T) value);
	}

}
