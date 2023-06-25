package com.raos.autocode.math;

import com.raos.autocode.math.variable.Scope;

// A main expression with any other expression as a base, acts as a wrapper
public class MainExpression implements Expressable, DifferentiableExpression {
	// Return the main expression
	private Expression base;

	// Create the scopes
	private Scope variableScope;

	// Wrap the expression
	public MainExpression() {

		// Create variable scope
		variableScope = new Scope();

	}

	public void setBase(Expression base) {
		// Set the base expression
		this.base = base;

		// Set the expression to this
		base.setMainExpression(this);
	}

	// Return the result
	@Override
	public <T> T result(NumberFormat<T> format) {
		if (base == null)
			throw new NullPointerException("No base expression defined");
		// Call the base format
		return base.result(format);
	}

	// Differentiates the expression
	@Override
	public Expression differentiate(String name) {
		// Differentiates the wrapped expression
		return base.differentiate(name);
	}

	// Accessors and Mutators
	public Scope getVariableScope() {
		return variableScope;
	}

	public void setVariableScope(Scope variableScope) {
		this.variableScope = variableScope;
	}

}
