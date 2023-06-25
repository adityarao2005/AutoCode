package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.NumberFormat;

// Division expression - represents the division operation between 2 expressions
class DivisionExpression extends OperationalExpression {
	// Fields
	private Expression numerator;
	private Expression denomanator;

	// Constructor
	public DivisionExpression(Expression first, Expression denomanator) {
		// Set the values
		this.numerator = first;
		this.denomanator = denomanator;
	}

	// Return the result of their addition
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Divide the values of the second from the first expression then format them
		return format.format(numerator.eval() / denomanator.eval());
	}

	// Return differentiated division expression
	@Override
	public Expression differentiate(String name) {
		// d/dx(f(x)/g(x)) = (f'(x)*g(x) - g'(x)*f(x))/(g(x))^2

		// (f'(x)*g(x) - g'(x)*f(x))/(g(x))^2
		return new DivisionExpression(
				// (f'(x)*g(x) - g'(x)*f(x))
				Operations.subtract(
						// f'(x) * g(x)
						Operations.multiply(numerator.differentiate(name), denomanator),
						// g'(x) * f(x)
						Operations.multiply(denomanator.differentiate(name), numerator)),
				// (g(x))^2
				Operations.power(
						// g(x)
						denomanator,
						// 2
						Expression.ofConstant(2)));
	}

	// Set the scope of the two sub functions
	@Override
	protected void setScopeForChildren() {
		// Set the scopes
		numerator.setMainExpression(getMainExpression());
		denomanator.setMainExpression(getMainExpression());
	}
}
