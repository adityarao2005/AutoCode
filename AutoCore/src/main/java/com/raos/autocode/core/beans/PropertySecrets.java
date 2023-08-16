package com.raos.autocode.core.beans;

import java.lang.reflect.Method;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.util.AnnotationRegistry;

@ClassPreamble(author = "Aditya Rao", date = "8/16/2023")
public class PropertySecrets {

	private String propertyName;
	private Class<?> propertyType;
	private AnnotationRegistry registry;

	public PropertySecrets(Method propertyMethod) {
		propertyType = propertyMethod.getReturnType();
		propertyName = propertyMethod.getName();
		registry = new AnnotationRegistry(propertyMethod);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class<?> propertyType) {
		this.propertyType = propertyType;
	}

	public AnnotationRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(AnnotationRegistry registry) {
		this.registry = registry;
	}

}
