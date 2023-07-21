package com.raos.autocode.core.beans.property;

import java.util.List;

import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

public interface ObservableProperty<T> extends Property<T> {

	// Get the listeners
	List<PropertyChangeListener<T>> getListeners();

	// Get the filters
	List<PropertyChangeFilter<T>> getFilters();

}