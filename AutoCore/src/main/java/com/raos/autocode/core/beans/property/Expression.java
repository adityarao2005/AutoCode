package com.raos.autocode.core.beans.property;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents an expression
 * @author aditya
 * @date Dec. 17, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface Expression<T> extends ReadableValue<T> {

	/**
	 * Returns the dependencies of this expression
	 * @return
	 */
	ReadableValue<?>[] getDependancies();
}
