package com.raos.autocode.core.beans.property.flavour;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

/**
 * Marks a property as observable and can have other people listen onto it
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface ObservableProperty<T> extends Property<T> {

	/**
	 * Adds a property change listener
	 * @param listener
	 */
	void addListener(PropertyChangeListener<T> listener);

	/**
	 * Removes a property change listener
	 * @param listener
	 */
	void removeListener(PropertyChangeListener<T> listener);
}
