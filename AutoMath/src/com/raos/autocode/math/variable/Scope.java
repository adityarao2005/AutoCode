package com.raos.autocode.math.variable;

import java.util.HashMap;
import java.util.Map;

// Represents a scope for the variables
public class Scope {

	// Variable map
	// TODO: Change implementation of this to: Map<String, Expression>
	// for regular expressions
	private Map<String, Double> variableMap;

	// Constructor
	public Scope() {
		variableMap = new HashMap<>();
	}

	// Create a variable
	public void createVariable(String variableName) {
		variableMap.put(variableName, Double.NaN);
	}

	// Set a value to the variable
	public void setVariable(String variableName, double value) {
		variableMap.replace(variableName, value);
	}

	// Remove the variable
	public void removeVariable(String variableName) {
		variableMap.remove(variableName);
	}

	// Get value
	public double getVariable(String variableName) {
		return variableMap.get(variableName);
	}

}
