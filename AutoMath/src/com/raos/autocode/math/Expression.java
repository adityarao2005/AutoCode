package com.raos.autocode.math;

import com.raos.autocode.math.operation.Operations;
import com.raos.autocode.math.variable.Scope;

// New API to parse and apply mathematical operations
public interface Expression extends DifferentiableExpression, Expressable {
	// Most important real constants
	public static final Expression ONE = Expression.ofConstant(1);
	public static final Expression NEG_ONE = Expression.ofConstant(-1);
	public static final Expression ZERO = Expression.ofConstant(0);
	public static final Expression PI = Expression.ofConstant(Math.PI);
	public static final Expression E = Expression.ofConstant(Math.E);

	// Constants class
	static class Constants {
		// Scope for constants
		private static final Scope CONSTANT_SCOPE = new Scope();

		static {

			// Initialize the constant scope
			CONSTANT_SCOPE.declareValue("PI", PI);
			CONSTANT_SCOPE.declareValue("E", E);
		}

	}

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
		// If the expr is a constant then no need to perform actions
		if (expr instanceof ConstantExpression)
			return expr;

		return ofConstant(expr.eval());
	}

	// Turns expression to constant
	public static Expression getConstant(String constantName) {
		return toConstant(Constants.CONSTANT_SCOPE.getValue(constantName));
	}

	// Add constant to scope
	public static void createConstant(String constantName, Expression expr) {
		Constants.CONSTANT_SCOPE.setValue(constantName, toConstant(expr));
	}

	// Negates the expression,
	public static Expression negate(Expression expr) {
		return Operations.multiply(NEG_ONE, expr);
	}

}