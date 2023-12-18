package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Simple annotation to mark methods that need to be todo'd
 * @author adity
 * @date Dec. 17, 2023
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE_USE)
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public @interface ToDo {

	/**
	 * @return the description of what needs to be done
	 */
	String description();
}
