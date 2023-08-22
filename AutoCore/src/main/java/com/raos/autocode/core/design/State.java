package com.raos.autocode.core.design;

// Meant to be implemented like a combination of a stack and a linkedlist
public interface State {
	// Gets the next state, if there is one
	State getNext();

	// Gets the previous state, if there is one
	State getPrevious();

	// Makes a change and creates a new state (with this state as the new state's previous state)
	State makeChange(String property, Object value);

}
