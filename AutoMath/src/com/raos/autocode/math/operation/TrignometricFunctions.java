package com.raos.autocode.math.operation;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Wrapper for trignometric functions
interface TrignometricFunctions {

	// Represents the expression: sin(f(x))
	static class SineExpression extends ExpressionBase implements OperationalExpression {
		// f(x)
		private Expression angle;

		// takes in parameter f(x)
		public SineExpression(Expression angle) {
			// Set the value
			this.angle = angle;
		}

		// Return the result
		@Override
		public <T> T result(NumberFormat<T> format) {
			// Return the result of sin(f(x))
			return format.format(Math.sin(angle.eval()));
		}

		// Differentiate
		@Override
		public Expression differentiate(String name) {
			// d/dx(sin[f(x)]) = cos[f(x)] * f'(x)
			return new MultiplyExpression(
					// cos[f(x)]
					new CosExpression(
							// f(x)
							angle),
					// f'(x)
					angle.differentiate(name));
		}

		// Set the scope
		@Override
		protected void setScopeForChildren() {
			angle.setScope(getScope());
		}

	}

	// Represents the expression: cos(f(x))
	static class CosExpression extends ExpressionBase implements OperationalExpression {
		// f(x)
		private Expression angle;

		// takes in parameter f(x)
		public CosExpression(Expression angle) {
			// Set the value
			this.angle = angle;
		}

		// Return the result
		@Override
		public <T> T result(NumberFormat<T> format) {
			// Return the result of cos(f(x))
			return format.format(Math.cos(angle.eval()));
		}

		// Differentiate
		@Override
		public Expression differentiate(String name) {
			// d/dx(cos[f(x)]) = -sin[f(x)] * f'(x)
			return new MultiplyExpression(
					// -sin[f(x)]
					Expression.negate(
							// sin[f(x)]
							new SineExpression(
									// f(x)
									angle)),
					// f'(x)
					angle.differentiate(name));

		}

		// Set the scope
		@Override
		protected void setScopeForChildren() {
			angle.setScope(getScope());
		}

	}

}
