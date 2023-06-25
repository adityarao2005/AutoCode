package com.raos.autocode.math.operation.function;

import java.util.function.BinaryOperator;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.NumberFormat;

// Represents a function with a value that can be based
public class BinaryFunction extends FunctionalExpression {
	// Function value
	private Expression value;
	private String name;

	// Constructor
	public BinaryFunction(String name, Expression first, Expression second, BinaryOperator<Expression> function) {
		// Set expr as parameter
		super(first, second);

		// Function
		this.value = function.apply(first, second);
	}

	// Differentiates
	@Override
	public Expression differentiate(String name) {
		return value.differentiate(name);
	}

	// Return result of value
	@Override
	public <T> T result(NumberFormat<T> format) {
		return value.result(format);
	}

	// Return name of defined function
	@Override
	public String getName() {
		return name;
	}

}
