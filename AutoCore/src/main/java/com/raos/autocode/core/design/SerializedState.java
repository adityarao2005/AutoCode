package com.raos.autocode.core.design;

import java.io.Serializable;
import java.util.Map;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.util.MapBuilder;

// Creates a new SerializedState
@ClassPreamble(author = "Aditya Rao", date = "8/21/2023")
public class SerializedState implements State, Serializable {
	private static final long serialVersionUID = 1L;

	// Previous and next states
	private SerializedState previous;
	private SerializedState next;
	// properties
	private Map<String, Object> properties;

	// Create beginning of states
	// starts null
	public SerializedState() {
		this(Map.of());
	}

	// Create a new map
	private SerializedState(Map<String, Object> properties) {
		this.properties = properties;
	}

	@Override
	public State getPrevious() {
		return previous;
	}

	@Override
	public State getNext() {
		return next;
	}

	private void setNext(SerializedState next) {
		this.next = next;
		next.previous = this;
	}

	// Create a new state by making change to property
	@Override
	public State makeChange(String property, Object value) {
		cutOfHead();
		SerializedState next = new SerializedState(MapBuilder.create(properties).addEntry(property, value).build());
		this.setNext(next);
		return next;
	}

	// Cut of next values
	private void cutOfHead() {
		next.previous = null;
		next = null;
	}

}
