package com.raos.autocode.core.annotation.beans.property;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

// TODO: implement this in the beans stuff
@ClassPreamble(author = "Aditya Rao", date = "8/9/2023")
@Documented
@Retention(SOURCE)
@Target(METHOD)
public @interface DecoratorMethod {

	String methodName();
}
