package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.operation.TrignometricFunctions.CosExpression;
import com.raos.autocode.math.operation.TrignometricFunctions.SineExpression;

// Holder class for expressions which are operations
public interface OperationalExpression extends Expression {

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

	// Returns a new power expression
	public static Expression ln(Expression a) {
		// Returns a logarithm expression
		return new LogarithimicExpression(a);
	}

	// Returns a new power expression
	public static Expression log(Expression a, double base) {
		// performs log function
		return log(a, Expression.ofConstant(base));
	}

	// Returns a new power expression
	public static Expression log(Expression a, Expression base) {
		// performs log function
		// log b (a) => log a/ log b
		return new DivisionExpression(
				// log a
				new LogarithimicExpression(a),
				// log b
				new LogarithimicExpression(base));
	}

	// Return sin(angle)
	public static Expression sin(Expression angle) {
		return new SineExpression(angle);
	}

	// Return cos(angle)
	public static Expression cos(Expression angle) {
		return new CosExpression(angle);
	}

	// Return tan(angle)
	public static Expression tan(Expression angle) {
		// tan(angle) = sin(angle)/cos(angle)
		return new DivisionExpression(
				// sin(angle)
				new SineExpression(angle),
				// cos(angle)
				new CosExpression(angle));

	}
}
