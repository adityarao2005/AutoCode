package com.raos.autocode.core.beans.property.event;

import com.raos.autocode.core.beans.property.Property;

public interface PropertyChangeFilter<T> {
	// Allowed filters
	public boolean filter(Property<T> property, T newv);
}
