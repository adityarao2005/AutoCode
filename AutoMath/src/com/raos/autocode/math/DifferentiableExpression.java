package com.raos.autocode.math;

// Represents an expression which is differentiable
public interface DifferentiableExpression {

	// Differentiate a function with respect to the variable name
	Expression differentiate(String name);

}
