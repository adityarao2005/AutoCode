package com.raos.autocode.core.beans.property;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Create a writable value
 * @author adity
 * @date Dec. 17, 2023
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface WritableValue<T> {
	
	/**
	 * Sets the value of the property
	 * @param value
	 */
	void setValue(T value);

}
