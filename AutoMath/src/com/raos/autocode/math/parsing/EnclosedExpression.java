package com.raos.autocode.math.parsing;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.ExpressionBase;
import com.raos.autocode.math.NumberFormat;

// Expression within brackets
public class EnclosedExpression extends ExpressionBase {
	private Expression base;

	private EnclosedExpression(String str) {
		/// TODO: 
	}
	
	@Override
	public Expression differentiate(String name) {
		return base.differentiate(name);
	}

	@Override
	public <T> T result(NumberFormat<T> format) {
		return base.result(format);
	}

	@Override
	protected void setScopeForChildren() {

		base.setMainExpression(getMainExpression());
	}
	
	public static EnclosedExpression parse(String expression) {
		return new EnclosedExpression(expression);
	}

}
