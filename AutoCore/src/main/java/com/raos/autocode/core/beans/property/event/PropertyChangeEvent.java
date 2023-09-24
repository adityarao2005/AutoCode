package com.raos.autocode.core.beans.property.event;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.PropertyManager;

/**
 * Represents a property change event
 * 
 * @author adity
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public class PropertyChangeEvent<T> {
	// Fields
	private PropertyManager bean;
	private T oldValue;
	private T newValue;

	// Constructor
	public PropertyChangeEvent(PropertyManager bean, T oldValue, T newValue) {
		super();
		this.bean = bean;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	// Getters and Setters
	public PropertyManager getBean() {
		return bean;
	}

	public void setBean(PropertyManager bean) {
		this.bean = bean;
	}

	public T getOldValue() {
		return oldValue;
	}

	public void setOldValue(T oldValue) {
		this.oldValue = oldValue;
	}

	public T getNewValue() {
		return newValue;
	}

	public void setNewValue(T newValue) {
		this.newValue = newValue;
	}

}
