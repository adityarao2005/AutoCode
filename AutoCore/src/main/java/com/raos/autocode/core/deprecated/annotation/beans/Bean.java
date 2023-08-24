package com.raos.autocode.core.deprecated.annotation.beans;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Used to describe an AutoBean (a special type of bean using dynamic proxies)
 * @author Raos
 *
 */
@Deprecated(forRemoval = true)
@ClassPreamble(author = "Aditya Rao", currentRevision = 1, date = "Jun 20, 2023")
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Bean {
}
