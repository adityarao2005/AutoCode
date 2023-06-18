package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Logarithimic expression - represents the natural logarithm
class LogarithimicExpression extends ExpressionBase implements OperationalExpression {
	// Fields
	private Expression value;

	// Constructor
	public LogarithimicExpression(Expression value) {
		// Set the values
		this.value = value;
	}

	// Return the power
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Take the exponent of the base to the exponent and format them
		return format.format(Math.log(value.eval()));
	}

	// Return the differentiated expression
	@Override
	public Expression differentiate(String name) {
		// f(x) = ln( g(x) )
		// f'(x) = g'(x) / g(x)
		return new DivisionExpression(
				// g'(x)
				value.differentiate(name),
				// g(x)
				value);
	}

	// Set the scope of the two sub functions
	@Override
	protected void setScopeForChildren() {
		// Set the scopes
		value.setScope(getScope());
	}
}
