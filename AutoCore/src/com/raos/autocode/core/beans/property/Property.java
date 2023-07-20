package com.raos.autocode.core.beans.property;

// Should go to beans project
public interface Property<T> extends Pointer<T> {

	// Gets the name of the property
	String getName();

	// Gets the owner of the property
	Object getBean();
	
	// Gets the type of the property
	Class<T> getType();

}
