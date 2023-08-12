package com.raos.autocode.core.test;

import java.util.logging.Logger;

import com.raos.autocode.core.annotation.beans.Bean;
import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Destructor;
import com.raos.autocode.core.annotation.beans.Init;
import com.raos.autocode.core.annotation.beans.Observable;
import com.raos.autocode.core.annotation.beans.ObserverListenerClass;
import com.raos.autocode.core.annotation.beans.ObserverListenerMethod;
import com.raos.autocode.core.annotation.beans.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.ObserverFilterMethod;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.event.PropertyChangeHandler;

@Bean
public interface StudentBean {

	@BeanProperty(nullable = false, type = String.class)
	public Property<String> username();

	@Observable
	@ObserverListenerMethod(methodName = "passwordChanged")
	@ObserverFilterMethod(methodName = "validatePassword", errorMessage = "Error! password not good")
	@BeanProperty(nullable = false, type = String.class)
	public Property<String> password();

	@Observable
	@BeanProperty(nullable = false, type = Integer.class)
	@ObserverListenerClass(listenerClass = AgeHandler.class)
	@ObserverFilterClass(filterClass = AgeHandler.class)
	public Property<Integer> age();

	@Init
	private void init() {
		System.out.println("Bean created!");
		System.out.println(this);
	}

	@Destructor
	private void destroy() {
		System.out.println(this);
		System.out.println("Bean Destroyed");
	}
	
	public default void printStack() {
		System.out.println(username());
		
		System.out.println("Stack printed!!");
	}

	public static class AgeHandler implements PropertyChangeHandler<Integer> {

		@Override
		public void onChange(Property<Integer> property, Integer old, Integer newv) {
			System.out.printf("Old value: %d, New value: %d%n", old, newv);
		}

		@Override
		public boolean filter(Property<Integer> property, Integer newv) {
			return newv > 5;
		}

		@Override
		public void onError(Integer invalidValue) {
			System.err.printf("Invalid value: %d%n", invalidValue);
		}
	}

//
//	// Allowed listeners:
	public default void passwordChanged(Property<String> property, String old, String newv) {
		System.out.println("password changed: " + newv);
	}

//
//	// Allowed filters types
	public default boolean validatePassword(Property<String> property, String newv) {
		// Return false to signal invalid parameter
		if (newv == null || newv.length() < 8)
			return false;
		return true;
	}
}
