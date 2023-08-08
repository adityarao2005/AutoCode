package com.raos.autocode.core.beans;

public class BeansInstantationException extends Exception {
	private static final long serialVersionUID = 1L;

	public BeansInstantationException() {
		super();
	}

	public BeansInstantationException(String message) {
		super(message);
	}

	public BeansInstantationException(String message, Throwable cause) {
		super(message, cause);
	}

}
