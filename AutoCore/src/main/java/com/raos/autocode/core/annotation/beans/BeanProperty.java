package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Used to describe a property for an AutoBean
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", currentRevision = 1, date = "Jun 20, 2023")
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface BeanProperty {

	// Checks whether null values are allowed
	boolean nullable() default true;
	
	// Embedded type
	Class<?> type();
	
}
