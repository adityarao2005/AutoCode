package com.raos.autocode.core.design.builder;

import java.beans.Expression;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.util.ExceptionUtil;

// Create a new constructable builder - builder which invokes a specialized constructor
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public class ConstructableBuilder<T> extends AbstractBuilder<T> {
	// Fields
	private Object[] ctorArgs;
	private Class<T> clazz;

	// Protected constructor
	protected ConstructableBuilder(Class<T> clazz) {
		ctorArgs = new Object[] {};
		this.clazz = clazz;
	}

	// Set ctor args
	public ConstructableBuilder<T> setCtorArgs(Object... args) {
		ctorArgs = args;

		return this;
	}

	// New instance
	@SuppressWarnings("unchecked")
	@Override
	public T newInstance() {
		return (T) ExceptionUtil.throwSilently(() -> new Expression(clazz, "new", ctorArgs).getValue());
	}

}
