package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.NumberFormat;
import com.raos.autocode.math.operation.function.Functions;

// Exponentiation expression - represents the power operation between 2 expressions
class ExponentiatonExpression extends OperationalExpression {
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
		return Operations.multiply(
				// h(x) ^ g(x)
				this,
				// ( g'(x) * ln( h(x) )) + (h'(x) * g(x) / h(x) )
				Operations.add(
						// g'(x) * ln( h(x) )
						Operations.multiply(
								// g'(x)
								exponent.differentiate(name),
								// ln( h(x) )
								Functions.ln(base)),
						// d/dx( ln( h(x) ) ) * g(x)
						Operations.multiply(
								// d/dx( ln( h(x) ) )
								Functions.ln(base).differentiate(name),
								// g(x)
								exponent)));
	}

	// Set the scope of the two sub functions
	@Override
	protected void setScopeForChildren() {
		// Set the scopes
		base.setMainExpression(getMainExpression());
		exponent.setMainExpression(getMainExpression());
	}
}
