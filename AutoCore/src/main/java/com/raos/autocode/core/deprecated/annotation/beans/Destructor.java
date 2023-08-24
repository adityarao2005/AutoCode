package com.raos.autocode.core.deprecated.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Deprecated(forRemoval = true)
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Destructor {

}
