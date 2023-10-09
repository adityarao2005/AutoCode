package com.raos.autocode.net;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * 
 * @author adity
 * @date Oct. 9, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Oct. 9, 2023")
public interface NetBridge<T> {

	/**
	 * Initializes the IO stream
	 * 
	 * @param input
	 * @param output
	 */
	public void initIO(ContentInput<T> input, ContentOutput<T> output);
}
