package com.raos.autocode.core.annotations.beans.property;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Mark property with this to make it validatable
 * 
 * @author aditya
 *
 */

@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface Validatable {

}
