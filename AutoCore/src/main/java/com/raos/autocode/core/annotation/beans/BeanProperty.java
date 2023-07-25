package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface BeanProperty {

	// Name of property, if it is "##default" then it will use regular name
	String name() default "##default";
	
	// Checks whether null values are allowed
	boolean nullable() default true;
	
	// Required on initialization
	boolean required() default false;
	
	// Embedded type
	Class<?> type();
	
}
