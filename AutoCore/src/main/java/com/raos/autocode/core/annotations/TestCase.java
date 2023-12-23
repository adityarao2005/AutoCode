package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@ClassPreamble(author = "Aditya Rao", date = "Dec. 22, 2023")
public @interface TestCase {

	String value();
}
