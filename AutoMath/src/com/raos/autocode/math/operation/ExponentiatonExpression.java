package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Exponentiation expression - represents the power operation between 2 expressions
class ExponentiatonExpression extends ExpressionBase implements OperationalExpression {
	// Fields
	private Expression base;
	private Expression exponent;

	// Constructor
	public ExponentiatonExpression(Expression base, Expression exponent) {
		// Set the values
		this.base = base;
		this.exponent = exponent;
	}

	// Return the power
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Take the exponent of the base to the exponent and format them
		return format.format(Math.pow(base.eval(), exponent.eval()));
	}

	// Return the differentiated expression
	@Override
	public Expression differentiate(String name) {
		// f(x) = h(x) ^ g(x)
		// ln( f(x) ) = g(x) ln ( h(x) )
		// f'(x) / f(x) = g'(x) * ln( h(x) ) + d/dx( ln( h(x) ) ) * g(x)
		// f'(x) = (h(x) ^ g(x)) * ( g'(x) * ln( h(x) ) + d/dx( ln( h(x) ) ) * g(x) )
		return new MultiplyExpression(
				// h(x) ^ g(x)
				this,
				// ( g'(x) * ln( h(x) )) + (h'(x) * g(x) / h(x) )
				new AddExpression(
						// g'(x) * ln( h(x) )
						new MultiplyExpression(
								// g'(x)
								exponent.differentiate(name),
								// ln( h(x) )
								new LogarithimicExpression(base)),
						// d/dx( ln( h(x) ) ) * g(x)
						new MultiplyExpression(
								// d/dx( ln( h(x) ) )
								new LogarithimicExpression(base).differentiate(name),
								// g(x)
								exponent)));
	}

	// Set the scope of the two sub functions
	@Override
	protected void setScopeForChildren() {
		// Set the scopes
		base.setScope(getScope());
		exponent.setScope(getScope());
	}
}
