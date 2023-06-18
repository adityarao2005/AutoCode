package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Multiply expression - represents the multiplication operation between 2 expressions
class MultiplyExpression extends ExpressionBase implements OperationalExpression {
	// Fields
	private Expression first;
	private Expression second;

	// Constructor
	public MultiplyExpression(Expression first, Expression second) {
		// Set the values
		this.first = first;
		this.second = second;
	}

	// Return the result of their addition
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Multiply the values of the first and second expression then format them
		return format.format(first.eval() * second.eval());
	}

	// differentiate the expression
	@Override
	public Expression differentiate(String name) {
		// d/dx( f(x) * g(x) ) = f'(x) * g(x) + g'(x) * f(x)

		// f'(x) * g(x) + g'(x) * f(x)
		return new AddExpression(
				// f'(x) * g(x)
				new MultiplyExpression(
						// f'(x)
						first.differentiate(name),
						// g(x)
						second),
				// g'(x) * f(x)
				new MultiplyExpression(
						// g'(x)
						second.differentiate(name),
						// f(x)
						first));
	}

	// Set the scope of the two sub functions
	@Override
	protected void setScopeForChildren() {
		// Set the scopes
		first.setScope(getScope());
		second.setScope(getScope());
	}
}
