package com.raos.autocode.math;

// Creates a new expression base
public abstract class ExpressionBase implements Expression {

	// Link to main expression
	private MainExpression mainExpression;

	public final MainExpression getMainExpression() {
		return mainExpression;
	}

	public final void setMainExpression(MainExpression mainExpression) {
		this.mainExpression = mainExpression;

		setScopeForChildren();
	}

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
