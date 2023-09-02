package com.raos.autocode.core.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows ppl to mark any type use as something to work on and give a description as to what needs to be done
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "8/9/2023")
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE_USE)
@Repeatable(ToDos.class)
public @interface ToDo {

	String description();
}
