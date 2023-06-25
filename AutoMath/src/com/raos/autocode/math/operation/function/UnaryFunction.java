package com.raos.autocode.math.operation.function;

import java.util.function.UnaryOperator;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.NumberFormat;

// Represents a function with a value that can be based
public class UnaryFunction extends FunctionalExpression {
	// Function value
	private Expression value;
	private String name;

	// Constructor
	public UnaryFunction(String name, Expression expr, UnaryOperator<Expression> function) {
		// Set expr as parameter
		super(expr);

		// Function
		this.value = function.apply(expr);
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
