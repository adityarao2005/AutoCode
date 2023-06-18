package com.raos.autocode.math;

import com.raos.autocode.math.operation.OperationalExpression;
import com.raos.autocode.math.variable.Scope;

// New API to parse and apply mathematical operations
public interface Expression extends DifferentiableExpression {
	// Most important real constants
	public static final Expression ONE = Expression.ofConstant(1);
	public static final Expression NEG_ONE = Expression.ofConstant(-1);
	public static final Expression ZERO = Expression.ofConstant(0);
	public static final Expression PI = Expression.ofConstant(Math.PI);
	public static final Expression E = Expression.ofConstant(Math.E);

	// Evaluates the expression and retsetScopeurns the result in the form given by
	// the
	// NumberFormat interface
	<T> T result(NumberFormat<T> format);

	// Get and set variable scope
	Scope getScope();

	void setScope(Scope scope);

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

	// Negates the expression,
	public static Expression negate(Expression expr) {
		return OperationalExpression.multiply(NEG_ONE, expr);
	}

}