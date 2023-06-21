package com.raos.autocode.math.operation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.plaf.basic.BasicSplitPaneDivider;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.operation.TrignometricFunctions.CosExpression;
import com.raos.autocode.math.operation.TrignometricFunctions.SineExpression;

// Holder class for expressions which are operations
public abstract class OperationalExpression extends ExpressionBase {

	public static interface Operations {
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

	// Holder for functions
	public static interface Functions {

		// Private class to hold map
		class FunctionMapHolder {
			// Map for functions
			private static final Map<String, Function<Expression, FunctionalExpression>> FUNCTIONS = new HashMap<>();

			static {
				// Keep default values
				FUNCTIONS.put("sin", expr -> new SineExpression(expr));
				FUNCTIONS.put("cos", expr -> new CosExpression(expr));
				FUNCTIONS.put("tan", expr -> {
					return new UnaryFunction("tan", expr, angle -> {
						// tan(angle) = sin(angle)/cos(angle)
						return Operations.divide(
								// sin(angle)
								new SineExpression(angle),
								// cos(angle)
								new CosExpression(angle));
					});
				});
				FUNCTIONS.put("log", expr -> new LogarithimicExpression(expr));
			}
		}

		// Returns a new power expression
		public static Expression ln(Expression a) {
			// Returns a logarithm expression
			return FunctionMapHolder.FUNCTIONS.get("log").apply(a);
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
			return Operations.divide(
					// log a
					ln(a),
					// log b
					ln(base));
		}

		// Return sin(angle)
		public static Expression sin(Expression angle) {
			return FunctionMapHolder.FUNCTIONS.get("sin").apply(angle);
		}

		// Return cos(angle)
		public static Expression cos(Expression angle) {
			return FunctionMapHolder.FUNCTIONS.get("cos").apply(angle);
		}

		// Return tan(angle)
		public static Expression tan(Expression angle) {
			return FunctionMapHolder.FUNCTIONS.get("tan").apply(angle);
		}

		// Get function
		public static Function<Expression, FunctionalExpression> getFunction(String name) {
			return FunctionMapHolder.FUNCTIONS.get(name);
		}

		// Adds a function
		public static void addFunction(String name, Function<Expression, FunctionalExpression> function) {
			FunctionMapHolder.FUNCTIONS.put(name, function);
		}
	}
}
