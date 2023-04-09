package com.raos.autocode.core.ds;

import java.util.Arrays;

// Tuple Class - Just for C++ people migrating to Java
public class Tuple {
	private Object[] objs;

	public Tuple(Object... objects) {
		this.objs = objects;
	}

	// Allows you to get a value from tuple
	@SuppressWarnings("unchecked")
	public <E> E get(int index) throws ClassCastException {
		return (E) objs[index];
	}
	
	// Hashcode
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(objs);
		return result;
	}
	
	// Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tuple other = (Tuple) obj;
		return Arrays.deepEquals(objs, other.objs);
	}
	
	public int getLength() {
		return objs.length;
	}

}
