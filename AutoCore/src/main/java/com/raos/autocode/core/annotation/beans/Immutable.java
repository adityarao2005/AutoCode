package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

// TODO: Rethink how to make this work
// Maybe make it a readonly property wrapper??!?!
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Immutable {

}
