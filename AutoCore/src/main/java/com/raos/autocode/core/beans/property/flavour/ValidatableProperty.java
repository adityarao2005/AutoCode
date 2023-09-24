package com.raos.autocode.core.beans.property.flavour;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.event.PropertyChangeValidator;

/**
 * Marks a property as validatable and can have other people validate its state
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface ValidatableProperty<T> extends Property<T> {

	/**
	 * Adds a property change validator
	 * 
	 * @param listener
	 */
	void addValidator(PropertyChangeValidator<T> validator);

	/**
	 * Removes a property change validator
	 * 
	 * @param listener
	 */
	void removeValidator(PropertyChangeValidator<T> validator);
}
