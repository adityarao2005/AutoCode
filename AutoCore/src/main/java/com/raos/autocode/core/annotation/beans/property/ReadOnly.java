package com.raos.autocode.core.annotation.beans.property;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

// This annotation is used to mark classes which can only be SET by their owner and readonly to the public
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ReadOnly {

}
