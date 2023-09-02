package com.raos.autocode.core.design.builder;

/**
 * Ancestor of all builders. Most simplest declaration
 * 
 * @author Raos
 *
 * @param <T>
 */
@FunctionalInterface
public interface Builder<T> {

	/**
	 * Builds the object
	 * 
	 * @return
	 */
	T build();
}
