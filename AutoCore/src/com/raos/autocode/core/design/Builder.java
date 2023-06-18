package com.raos.autocode.core.design;

// This class represents a generic builder for any class
// Any class which implements this interface must also specify all the properties
// On a long term scale, it reduces code and allows for object prototyping and recycling
// and makes everything more neat and tidy
public interface Builder<T> {

	// Builds the object
	T build();
}
