package com.raos.autocode.core.beans.property;

public interface PropertyChangeListener<T> {
	// On event action
	public void onChange(Property<T> property, T old, T newv);
}
