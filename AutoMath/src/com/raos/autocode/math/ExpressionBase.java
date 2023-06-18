package com.raos.autocode.math;

import com.raos.autocode.math.variable.Scope;

// Creates a new expression base
public abstract class ExpressionBase implements Expression {

	// Scope of object
	private Scope scope;

	// Retrieve the scope
	public final Scope getScope() {
		return scope;
	}

	// Set the scope
	public final void setScope(Scope scope) {
		// Set the scope for this object
		this.scope = scope;

		// Set scope for child expressions
		setScopeForChildren();
	}

	// Set scope for child expressions
	protected abstract void setScopeForChildren();

	// Default method
	@Override
	public boolean equals(Object other) {
		// Check for null
		if (other == null)
			return false;

		// Check for same super class
		if (!(other instanceof Expression))
			return false;

		// Check for evaluated expression
		return this.eval() == ((Expression) other).eval();
	}
}
