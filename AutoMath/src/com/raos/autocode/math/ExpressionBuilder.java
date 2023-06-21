package com.raos.autocode.math;

import java.util.HashMap;
import java.util.Map;

import com.raos.autocode.core.design.Builder;
import com.raos.autocode.math.parsing.ExpressionParser;

public abstract class ExpressionBuilder implements Builder<Expression> {
	private Map<String, Object> properties = new HashMap<>();
	private ExpressionParser parser;

	private ExpressionBuilder(ExpressionParser parser) {
		this.parser = parser;
	}

	public ExpressionBuilder setProperty(String property, Object value) {
		properties.put(property, value);

		return this;
	}

	public ExpressionBuilder setType(String property, Object value) {
		properties.put(property, value);

		return this;
	}

	@Override
	public Expression build() {

		
		
		return null;

//		return new Expression();
	}
	
	public enum ExpressionType {
		CONST, VAR, ADD, SUBTRACT, MULTIPLY, DIVIDE, EXP, FUNC;
		
		
		
	}
}
