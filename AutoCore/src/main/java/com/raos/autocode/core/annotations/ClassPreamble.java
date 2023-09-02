package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Allows ppl to give details as to who wrote the code and when it was written
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "8/9/2023")
@Documented
@Retention(SOURCE)
@Target(TYPE)
public @interface ClassPreamble {

	String author();

	String date();

	int currentRevision() default 1;

	String lastModified() default "N/A";

	String lastModifiedBy() default "N/A";

	// Note use of array
	String[] reviewers() default {};
}
