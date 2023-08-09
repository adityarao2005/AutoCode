package com.raos.autocode.core.annotation.beans;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ObserverListenerClass {

	// Listener class
	Class<? extends PropertyChangeListener<?>> listenerClass();
}
