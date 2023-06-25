package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;

public interface Operations {
	// Returns a new addition expression
	public static Expression add(Expression a, Expression b) {
		return new AddExpression(a, b);
	}

	// Returns a new addition expression
	public static Expression subtract(Expression a, Expression b) {
		return new AddExpression(a, b);
	}

	// Returns a new multiply expression
	public static Expression multiply(Expression a, Expression b) {
		return new MultiplyExpression(a, b);
	}

	// Returns a new divide expression
	public static Expression divide(Expression a, Expression b) {
		return new DivisionExpression(a, b);
	}

	// Returns a new power expression
	public static Expression power(Expression a, Expression b) {
		return new ExponentiatonExpression(a, b);
	}

}