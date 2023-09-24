package com.raos.autocode.core.beans.property.event;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Allows for listening onto property changes
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface PropertyChangeListener<T> {
	
	/**
	 * Callback when property has changed
	 * @param event
	 */
	void onChange(PropertyChangeEvent<T> event);
}
