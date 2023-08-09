package com.raos.autocode.core.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@ClassPreamble(author = "Aditya Rao", date = "8/9/2023")
@Documented
@Retention(SOURCE)
@Target(ElementType.TYPE_USE)
public @interface ToDos {

	ToDo[] value();
}
