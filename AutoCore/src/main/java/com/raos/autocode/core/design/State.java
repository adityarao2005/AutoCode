package com.raos.autocode.core.design;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Meant to be implemented like a combination of a stack and a linkedlist
 * 
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
public interface State {
	/**
	 * Gets the next state, if there is one
	 * 
	 * @return gets next state if present
	 */
	State getNext();

	/**
	 * Gets the previous state, if there is one
	 * 
	 * @return gets previous state if present
	 */
	State getPrevious();

	/**
	 * Makes a change and creates a new state (with this state as the new state's
	 * properties)
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	State makeChange(String property, Object value);

}
