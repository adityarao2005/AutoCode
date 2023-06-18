package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

@Documented
@Target(TYPE)
public @interface ToDo {

	String[] methods();

	String description();
}
