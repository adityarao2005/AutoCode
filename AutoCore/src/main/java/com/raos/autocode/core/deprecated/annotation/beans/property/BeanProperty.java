package com.raos.autocode.core.deprecated.annotation.beans.property;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Used to describe a property for an AutoBean
 * 
 * @author Raos
 *
 */
@Deprecated(forRemoval = true)
@ClassPreamble(author = "Aditya Rao", currentRevision = 1, date = "Jun 20, 2023")
@Documented
@Retention(RUNTIME)
@Target(value = { METHOD, ElementType.ANNOTATION_TYPE })
public @interface BeanProperty {

	// Embedded type
	Class<?> type();

}
