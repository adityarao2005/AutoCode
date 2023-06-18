package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Addition expression - represents the add operation between 2 expressions
class AddExpression extends ExpressionBase implements OperationalExpression {
	// Fields
	private Expression first;
	private Expression second;

	// Constructor
	public AddExpression(Expression first, Expression second) {
		// Set the values
		this.first = first;
		this.second = second;
	}

	// Return the result of their addition
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Add the values of the first and second expression then format them
		return format.format(first.eval() + second.eval());
	}

	// Differentiate the add
	@Override
	public Expression differentiate(String name) {
		// Differentiate the add
		// d/dx(f(x) + g(x)) = f'(x) + g'(x)
		return new AddExpression(
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
