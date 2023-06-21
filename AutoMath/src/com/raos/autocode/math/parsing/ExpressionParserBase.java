package com.raos.autocode.math.parsing;

import com.raos.autocode.math.Expression;
import com.raos.autocode.math.variable.Scope;

public abstract class ExpressionParserBase implements ExpressionParser {
	// Scopes
	private Scope constantScope;

	// Constructor
	public ExpressionParserBase() {
		// Create the scopes
		constantScope = new Scope();

		// Initialize the constant scope
		constantScope.declareValue("PI", Expression.PI);
		constantScope.declareValue("E", Expression.E);
	}

	public Scope getConstantScope() {
		return constantScope;
	}
}
