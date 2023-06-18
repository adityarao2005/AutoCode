package com.raos.autocode.math;

import com.raos.autocode.math.variable.Scope;

// A main expression with any other expression as a base, acts as a wrapper
public class MainExpression extends ExpressionBase {
	// Return the main expression
	private ExpressionBase base;

	// Wrap the expression
	public MainExpression(ExpressionBase base) {
		// Set the base expression
		this.base = base;
		
		// Set scope as default scope
		setScope(new Scope());
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

	// Set scope
	@Override
	protected void setScopeForChildren() {
		base.setScope(getScope());
	}

}
