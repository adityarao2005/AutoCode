package com.raos.autocode.core.design.builder;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Ancestor of all builders. Most simplest declaration
 * 
 * @author Raos
 *
 * @param <T>
 */
@FunctionalInterface
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface Builder<T> {

	/**
	 * Builds the object
	 * 
	 * @return
	 */
	T build();
}
