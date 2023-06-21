package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;

// A generic implementation for a functional expression
public abstract class FunctionalExpression extends OperationalExpression {
	// Expressions required to parse value
	private Expression[] params;

	// Set the expressions
	public FunctionalExpression(Expression... expressions) {
		this.params = expressions;
	}

	// Set scope for params
	@Override
	protected void setScopeForChildren() {
		for (Expression expr : params)
			expr.setMainExpression(getMainExpression());
	}

	// Get the name of the function
	public abstract String getName();

	// Get the param at index
	public Expression getParam(int index) {
		return params[index];
	}
	
	// get size of arguments
	public int sizeArgs() {
		return params.length;
	}
}
