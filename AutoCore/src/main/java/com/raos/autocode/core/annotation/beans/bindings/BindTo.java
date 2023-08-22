package com.raos.autocode.core.annotation.beans.bindings;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.function.Function;

import com.raos.autocode.core.annotations.ClassPreamble;

@ClassPreamble(author = "Aditya Rao", date = "8/21/2023")
@SuppressWarnings("rawtypes")
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface BindTo {

	// variable name
	String varName();

	// bidirectionality
	boolean bidirectional() default false;

	// needed if different types
	Class<? extends Function> forwardConverter() default DEFAULT.class;

	// needed if bidirectional and different types
	Class<? extends Function> reverseConverter() default DEFAULT.class;

	static interface DEFAULT<T, V> extends Function<T, V> {
	}

}
