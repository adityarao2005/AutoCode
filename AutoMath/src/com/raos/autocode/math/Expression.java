package com.raos.autocode.math;

import com.raos.autocode.math.operation.OperationalExpression.Operations;

// New API to parse and apply mathematical operations
public interface Expression extends DifferentiableExpression, Expressable {
	// Most important real constants
	public static final Expression ONE = Expression.ofConstant(1);
	public static final Expression NEG_ONE = Expression.ofConstant(-1);
	public static final Expression ZERO = Expression.ofConstant(0);
	public static final Expression PI = Expression.ofConstant(Math.PI);
	public static final Expression E = Expression.ofConstant(Math.E);

	// Main Expressions
	public MainExpression getMainExpression();

	public void setMainExpression(MainExpression mainExpression);

	// Creates a new constant expression
	public static Expression ofConstant(double value) {
		return new ConstantExpression(value);
	}

	// Evaluates the expression in the form of a double
	public default double eval() {
		return result(NumberFormat.DOUBLE_FORMAT);
	}

	// Turns expression to constant
	public static Expression toConstant(Expression expr) {
		return new ConstantExpression(expr.eval());
	}

	// Turns expression to constant
	public static Expression getConstant(String constantName, MainExpression main) {
		return toConstant(main.getConstantScope().getValue(constantName));
	}

	// Negates the expression,
	public static Expression negate(Expression expr) {
		return Operations.multiply(NEG_ONE, expr);
	}

}