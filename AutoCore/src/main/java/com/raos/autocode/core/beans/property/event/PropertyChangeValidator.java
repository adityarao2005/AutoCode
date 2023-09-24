package com.raos.autocode.core.beans.property.event;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Validates whether a write request to the property is allowed for the
 * specified value
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface PropertyChangeValidator<T> {

	/**
	 * Call this method when invalid
	 * 
	 * @param event
	 */
	public default void onInvalid(PropertyChangeEvent<T> event) {
	}

	/**
	 * Validates whether new value is allowed
	 * 
	 * @param event
	 * @return
	 */
	public boolean validate(PropertyChangeEvent<T> event);
}
