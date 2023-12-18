package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Describes the purpose of a class
 * @author adity
 * @date Dec. 17, 2023
 *
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public @interface ClassPreamble {

	/**
	 * @return the name of the author
	 */
	String author();
	
	/**
	 * @return the date of which it was created
	 */
	String date();
}
