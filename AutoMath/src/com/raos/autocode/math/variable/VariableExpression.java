package com.raos.autocode.math.variable;

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
		// Return result
		return format.format(getScope().getVariable(name));
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
