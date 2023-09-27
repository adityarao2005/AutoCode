package com.raos.autocode.core.util;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Utilities for objects
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface ObjectUtil {
	
	/**
	 * Require type
	 * @param obj
	 * @param type
	 */
	public static void requireType(Object obj, Class<?> type) {
		if (type.isInstance(obj))
			throw new ClassCastException("Object passed must be of type: " + type);
	}
}
