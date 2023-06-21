package com.raos.autocode.math.variable;

import java.util.HashMap;
import java.util.Map;

import com.raos.autocode.math.Expression;

// Represents a scope for the values
public final class Scope {

	// Value map
	// for regular expressions
	private Map<String, Expression> valueMap;

	// Constructor
	public Scope() {
		valueMap = new HashMap<>();
	}

	// Create a value
	public void declareValue(String valueName) {
		declareValue(valueName, null);
	}

	// Create a value
	public void declareValue(String valueName, Expression value) {
		valueMap.put(valueName, value);
	}

	// Set a value to the value
	public void setValue(String valueName, Expression value) {
		valueMap.replace(valueName, value);
	}

	// Remove the value
	public void removeValue(String valueName) {
		valueMap.remove(valueName);
	}

	// Get value
	public Expression getValue(String valueName) {
		return valueMap.get(valueName);
	}

	// Check exists for value
	public boolean valueExists(String valueName) {
		return valueMap.get(valueName) != null;
	}

}
