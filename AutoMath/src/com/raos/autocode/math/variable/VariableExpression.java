package com.raos.autocode.math.variable;

import java.util.Objects;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Represents a variable
public class VariableExpression extends ExpressionBase {
	// Name of the variable
	private String name;

	// setter ctor
	public VariableExpression(String name) {
		this.name = name;
	}

	// Returns result of the variable
	@Override
	public <T> T result(NumberFormat<T> format) {
		// Get expression
		Expression expr = getMainExpression().getVariableScope().getValue(name);

		// Error check
		if (Objects.isNull(expr)) {
			throw new VariableNotFoundException(name);
		}

		// Return result
		return format.format(expr.eval());
	}

	// Differentiate
	@Override
	public Expression differentiate(String name) {
		// If the variable name is the same, return 1
		// otherwise return this
		return name.equals(this.name) ? ONE : ZERO;
	}

	// Do nothing
	@Override
	protected void setScopeForChildren() {
	}

}
