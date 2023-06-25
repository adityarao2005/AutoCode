package com.raos.autocode.math.operation.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.operation.Operations;
import com.raos.autocode.math.operation.function.TrignometricFunctions.CosExpression;
import com.raos.autocode.math.operation.function.TrignometricFunctions.SineExpression;

//Holder for functions
public interface Functions {

	// Private class to hold map
	class FunctionMapHolder {
		// Map for functions
		private static final Map<String, Function<Expression[], FunctionalExpression>> FUNCTIONS = new HashMap<>();

		static {
			// Keep default values
			FUNCTIONS.put("sin", params -> new SineExpression(params[0]));
			FUNCTIONS.put("cos", params -> new CosExpression(params[0]));
			FUNCTIONS.put("tan", params -> {
				return new UnaryFunction("tan", params[0], angle -> {
					// tan(angle) = sin(angle)/cos(angle)
					return Operations.divide(
							// sin(angle)
							new SineExpression(angle),
							// cos(angle)
							new CosExpression(angle));
				});
			});
			FUNCTIONS.put("ln", params -> new LogarithimicFunction(params[0]));
			FUNCTIONS.put("log", params -> {
				// Return the function of log(base, power)
				return new BinaryFunction("log", params[0], params[1], (first, second) -> {
					return Operations.divide(ln(first), ln(second));
				});
			});
			FUNCTIONS.put("log10", params -> {
				// Return function of log10
				return new UnaryFunction("log10", params[0], value -> {
					// Return log10
					return log(value, Expression.ofConstant(10));
				});
			});
		}
	}

	// Returns a new power expression
	public static Expression ln(Expression a) {
		// Returns a logarithm expression
		return FunctionMapHolder.FUNCTIONS.get("ln").apply(wrap(a));
	}

	// Returns a new power expression
	public static Expression log(Expression a, Expression base) {
		// Returns a logarithm expression
		return FunctionMapHolder.FUNCTIONS.get("log").apply(new Expression[] { a, base });
	}

	// Returns a new power expression
	public static Expression log(Expression a, double base) {
		// Returns a logarithm expression
		return FunctionMapHolder.FUNCTIONS.get("log10").apply(wrap(a));
	}

	// Return sin(angle)
	public static Expression sin(Expression angle) {
		return FunctionMapHolder.FUNCTIONS.get("sin").apply(wrap(angle));
	}

	// Return cos(angle)
	public static Expression cos(Expression angle) {
		return FunctionMapHolder.FUNCTIONS.get("cos").apply(wrap(angle));
	}

	// Return tan(angle)
	public static Expression tan(Expression angle) {
		return FunctionMapHolder.FUNCTIONS.get("tan").apply(wrap(angle));
	}

	// Get function
	public static Function<Expression[], FunctionalExpression> getFunction(String name) {
		return FunctionMapHolder.FUNCTIONS.get(name);
	}

	// Adds a function
	public static void addFunction(String name, Function<Expression[], FunctionalExpression> function) {
		FunctionMapHolder.FUNCTIONS.put(name, function);
	}

	// Utility method
	private static Expression[] wrap(Expression expr) {
		return new Expression[] { expr };
	}
}
