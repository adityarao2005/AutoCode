package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.beans.property.PropertyChangeFilter;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ObserverFilterClass {

	// Listener class
	Class<? extends PropertyChangeFilter<?>> filterClass();
}
