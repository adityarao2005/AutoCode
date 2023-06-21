package com.raos.autocode.math.parsing;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.MainExpression;

// Create the interface for parsing the expression
public interface ExpressionParser {

	// Method to turn string to expression
	Expression parse(String str);

	// Main expression
	public default MainExpression parseMain(String str) {
		return new MainExpression(parse(str));
	}
}
