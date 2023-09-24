package com.raos.autocode.core.annotations.beans.property;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Marks a property as a bean property
 * @author aditya
 *
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public @interface BeanProperty {

}
