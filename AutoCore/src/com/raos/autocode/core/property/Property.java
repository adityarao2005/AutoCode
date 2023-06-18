package com.raos.autocode.core.property;

public interface Property<T> extends Pointer<T> {

	// Gets the name of the property
	String getName();

	// Gets the owner of the property
	Object getBean();

}
