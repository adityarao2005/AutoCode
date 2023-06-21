package com.raos.autocode.math.variable;

public class VariableNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public VariableNotFoundException(String variableName) {
		super(String.format("The variable %s was not found in the scope", variableName));
	}

}
