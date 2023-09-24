package com.raos.autocode.core;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents a general aspect for Aspect Oriented Programming
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface Aspect {

	/**
	 * Adds a pre-processor
	 * 
	 * @param processor
	 */
	void addPreProcessor(PreProcessor processor);

	/**
	 * Adds a post processor
	 * 
	 * @param processor
	 */
	void addPostProcessor(PostProcessor processor);
}
