package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Subtraction expression - represents the subtract operation between 2 expressions
class SubtractExpression extends ExpressionBase implements OperationalExpression {
	// Fields
	private Expression first;
	private Expression second;

	// Constructor
	public SubtractExpression(Expression first, Expression second) {
		// Set the values
		this.first = first;
		this.second = second;
	}

	// Return the result of their addition
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Subtract the values of the second from the first expression then format them
		return format.format(first.eval() - second.eval());
	}

	// Differentiate the subtraction expression
	@Override
	public Expression differentiate(String name) {
		// Return the differentiated subtraction expression
		// d/dx(f(x) - g(x)) = f'(x) - g'(x)
		return new SubtractExpression(
				// f'(x)
				first.differentiate(name),
				// g'(x)
				second.differentiate(name));
	}

	// Set the scope of the two sub functions
	@Override
	protected void setScopeForChildren() {
		// Set the scopes
		first.setScope(getScope());
		second.setScope(getScope());
	}
}
