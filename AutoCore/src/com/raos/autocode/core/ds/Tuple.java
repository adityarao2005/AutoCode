package com.raos.autocode.core.ds;

import java.util.Arrays;

// Tuple Class - Just for C++ people migrating to Java
public class Tuple {
	// Holds the reference objects
	private Object[] objs;

	// Constructor for the objects
	public Tuple(Object... objects) {
		this.objs = objects;
	}

	// Generic accessor
	// Allows you to get a value from tuple
	@SuppressWarnings("unchecked")
	public <E> E get(int index) throws ClassCastException {
		return (E) objs[index];
	}

	// Hashcode
	// Hashes each object
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(objs);
		return result;
	}

	// Equals
	// Checks whether the tuples are equal to each other
	@Override
	public boolean equals(Object obj) {
		// Base cases
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		// Checks the arrays
		Tuple other = (Tuple) obj;
		return Arrays.deepEquals(objs, other.objs);
	}

	// Gets the length of the tuple
	public int length() {
		return objs.length;
	}

}
