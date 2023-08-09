package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ClassPreamble(author = "Aditya Rao", date = "8/9/2023")
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE_USE)
@Repeatable(ToDos.class)
public @interface ToDo {

	String description();
}
