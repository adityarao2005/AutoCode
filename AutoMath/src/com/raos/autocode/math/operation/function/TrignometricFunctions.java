package com.raos.autocode.math.operation.function;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.NumberFormat;
import com.raos.autocode.math.operation.Operations;

// Wrapper for trignometric functions
interface TrignometricFunctions {

	// Return a trig expression
	static abstract class TrigExpression extends FunctionalExpression {

		// takes in parameter f(x)
		public TrigExpression(Expression angle) {
			super(angle);
		}

		// Set the scope
		@Override
		protected void setScopeForChildren() {
			getAngle().setMainExpression(getMainExpression());
		}

		// Accessor
		public Expression getAngle() {
			return getParam(0);
		}

	}

	// Represents the expression: sin(f(x))
	static class SineExpression extends TrigExpression {
		// takes in parameter f(x)
		public SineExpression(Expression angle) {
			super(angle);
		}

		// Return the result
		@Override
		public <T> T result(NumberFormat<T> format) {
			// Return the result of sin(f(x))
			return format.format(Math.sin(getAngle().eval()));
		}

		// Differentiate
		@Override
		public Expression differentiate(String name) {
			// d/dx(sin[f(x)]) = cos[f(x)] * f'(x)
			return Operations.multiply(
					// cos[f(x)]
					new CosExpression(
							// f(x)
							getAngle()),
					// f'(x)
					getAngle().differentiate(name));
		}

		@Override
		public String getName() {
			return "sin";
		}

	}

	// Represents the expression: cos(f(x))
	static class CosExpression extends TrigExpression {

		// takes in parameter f(x)
		public CosExpression(Expression angle) {
			super(angle);
		}

		// Return the result
		@Override
		public <T> T result(NumberFormat<T> format) {
			// Return the result of cos(f(x))
			return format.format(Math.cos(getAngle().eval()));
		}

		// Differentiate
		@Override
		public Expression differentiate(String name) {
			// d/dx(cos[f(x)]) = -sin[f(x)] * f'(x)
			return Operations.multiply(
					// -sin[f(x)]
					Expression.negate(
							// sin[f(x)]
							new SineExpression(
									// f(x)
									getAngle())),
					// f'(x)
					getAngle().differentiate(name));

		}

		@Override
		public String getName() {
			return "cos";
		}

	}

}
