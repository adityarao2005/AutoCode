package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

@ClassPreamble(author = "Aditya Rao", date = "8/9/2023")
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Init {

}
