package com.raos.autocode.core.design;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Creates a new SerializedState. States are like properties or entries
 * 
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "8/21/2023")
public class SerializedState implements State, Serializable {
	private static final long serialVersionUID = 1L;

	// Previous and next states
	private SerializedState previous;
	private SerializedState next;
	// properties
	private String property;
	private Object value;

	/**
	 * Creates a new state with a default state values
	 * 
	 * @param property
	 * @param value
	 */
	public SerializedState(String property, Object value) {
		this.property = property;
		this.value = value;
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
		SerializedState next = new SerializedState(property, value);
		this.setNext(next);
		return next;
	}

	// Cut of next values
	private void cutOfHead() {
		next.previous = null;
		next = null;
	}

	/**
	 * Basically gets the most recent states and persists that
	 * 
	 * @param thiz
	 * @return
	 */
	public static Map<String, Object> compileStates(SerializedState thiz) {
		SerializedState prev = thiz;
		Map<String, Object> map = new HashMap<>();

		// While there isnt any more previous values
		while (prev != null) {
			// Adds newest change to map
			map.putIfAbsent(prev.property, prev.value);

			// Get the previous state
			prev = prev.previous;
		}

		// Return the map
		return map;
	}

}
