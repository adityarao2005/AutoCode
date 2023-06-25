package com.raos.autocode.math.operation.function;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.NumberFormat;
import com.raos.autocode.math.operation.Operations;

// Logarithimic expression - represents the natural logarithm
class LogarithimicFunction extends FunctionalExpression {

	// Constructor
	public LogarithimicFunction(Expression value) {
		super(value);
	}

	// Return the power
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Take the exponent of the base to the exponent and format them
		return format.format(Math.log(getValue().eval()));
	}

	// Return the differentiated expression
	@Override
	public Expression differentiate(String name) {
		// f(x) = ln( g(x) )
		// f'(x) = g'(x) / g(x)
		return Operations.divide(
				// g'(x)
				getValue().differentiate(name),
				// g(x)
				getValue());
	}

	// Get the value
	public Expression getValue() {
		return getParam(0);
	}

	// Expression
	@Override
	public String getName() {
		return "log";
	}
}
