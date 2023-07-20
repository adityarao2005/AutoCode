package com.raos.autocode.core.beans.property;

public interface PropertyChangeFilter<T> {
	// Allowed filters
	public boolean filter(Property<T> property, T newv);
}
