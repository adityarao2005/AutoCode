package com.raos.autocode.core.beans;

import java.lang.reflect.Method;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.design.builder.Builder;

// Acts as a registry
public interface PropertyRegistry {

	// Return a builder for the property
	Builder<Property<?>> register(Method method);

}
