package com.raos.autocode.core.application;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents an exception on an application level
 * @author adity
 * @date Dec. 20, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 20, 2023")
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Default no arg constructor
	 */
	public ApplicationException() {
		super();
	}

	/**
	 * Message and cause constructor
	 * @param message
	 * @param cause
	 */
	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Message constructor
	 * @param message
	 */
	public ApplicationException(String message) {
		super(message);
	}

	/**
	 * Throwable constructor
	 * @param cause
	 */
	public ApplicationException(Throwable cause) {
		super(cause);
	}

	
}
