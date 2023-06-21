package com.raos.autocode.math;

import com.raos.autocode.math.variable.Scope;

// A main expression with any other expression as a base, acts as a wrapper
public class MainExpression implements Expressable, DifferentiableExpression {
	// Return the main expression
	private Expression base;

	// Create the scopes
	private Scope variableScope;
	private Scope constantScope;

	// Wrap the expression
	public MainExpression(Expression base) {
		// Set the base expression
		this.base = base;

		// Create the scopes
		constantScope = new Scope();
		variableScope = new Scope();

		// Initialize the constant scope
		constantScope.declareValue("PI", Expression.PI);
		constantScope.declareValue("E", Expression.E);

		// Set the expression to this
		base.setMainExpression(this);

	}

	// Return the result
	@Override
	public <T> T result(NumberFormat<T> format) {
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

	public Scope getConstantScope() {
		return constantScope;
	}

	public void setVariableScope(Scope variableScope) {
		this.variableScope = variableScope;
	}

	public void setConstantScope(Scope constantScope) {
		this.constantScope = constantScope;
	}

}
