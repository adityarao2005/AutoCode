package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.beans.property.PropertyChangeListener;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ObserverChangeClass {

	// Listener class
	Class<? extends PropertyChangeListener<?>> listenerClass();
}
