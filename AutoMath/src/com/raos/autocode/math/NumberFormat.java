package com.raos.autocode.math;

// Turns double value into another format to house a number
public interface NumberFormat<T> {
	// Constants
	public static final NumberFormat<Expression> CONSTANT_FORMAT = ConstantExpression::new;
	public static final NumberFormat<Double> DOUBLE_FORMAT = e -> e;
	public static final NumberFormat<Integer> INTEGER_FORMAT = e -> (int) Math.round(e);

	// Formats to value
	T format(double value);

	// Returns a new expression format
	public static NumberFormat<Expression> constantFormatter() {
		return CONSTANT_FORMAT;
	}

	// Returns a double format
	public static NumberFormat<Double> doubleFormatter() {
		return DOUBLE_FORMAT;
	}

	// Returns a integer format
	public static NumberFormat<Integer> integerFormatter() {
		return INTEGER_FORMAT;
	}
}
