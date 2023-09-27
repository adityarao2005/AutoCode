package com.raos.autocode.core.util.functional;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Property Setter and returner. Allows for object mutation. Typically pattern
 * used by builders
 */
@FunctionalInterface
public interface SetterUnaryOperator<T> extends UnaryOperator<T>, Consumer<T> {

	/**
	 * Applies the change onto this object and returns result
	 */
	@Override
	public default T apply(T input) {
		accept(input);
		return input;
	}
}
