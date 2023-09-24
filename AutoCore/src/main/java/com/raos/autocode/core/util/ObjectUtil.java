package com.raos.autocode.core.util;

public final class ObjectUtil {
	private ObjectUtil() {
	}
	
	public static void requireType(Object obj, Class<?> type) {
		if (type.isInstance(obj))
			throw new ClassCastException("Object passed must be of type: " + type);
	}
}
