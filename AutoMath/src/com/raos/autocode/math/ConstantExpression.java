package com.raos.autocode.math;

// Represents a mathematical scalar constant such as 0, 1, e, pi
class ConstantExpression extends ExpressionBase {
	// Value
	private double value;

	// Constructor
	public ConstantExpression(double value) {
		this.value = value;
		
	}

	// Returns result transposed
	@Override
	public <T> T result(NumberFormat<T> format) {
		return format.format(value);
	}

	// Return 0
	@Override
	public Expression differentiate(String name) {
		// Return 0
		return ZERO;
	}

	// Does nothing cuz there isnt anything to set
	@Override
	protected void setScopeForChildren() {
	}

}
