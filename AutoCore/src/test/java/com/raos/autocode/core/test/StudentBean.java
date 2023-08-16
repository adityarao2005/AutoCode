package com.raos.autocode.core.test;


import com.raos.autocode.core.annotation.beans.Bean;
import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Destructor;
import com.raos.autocode.core.annotation.beans.Init;
import com.raos.autocode.core.annotation.beans.Observable;
import com.raos.autocode.core.annotation.beans.ObserverListenerClass;
import com.raos.autocode.core.annotation.beans.ObserverListenerMethod;
import com.raos.autocode.core.annotation.beans.ReadOnly;
import com.raos.autocode.core.annotation.beans.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.ObserverFilterMethod;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.event.PropertyChangeHandler;

// Annotation says that this class is a bean
// Gonna be used later for classpath searching annotation support
@Bean
public interface StudentBean {

	// @BeanProperty describes that this method is a property for the bean - later addition would be that this is not required (that is if we ever get to figure out what the generic value of methods are)
	// 	- describes whether this is null or what the type is
	// @ReadOnly
	//  - describes that only the StudentBean class can modify the field username
	@BeanProperty(nullable = false, type = String.class)
	@ReadOnly
	public Property<String> username();

	// @Observable shows that this method can be listened onto, listeners can be added or removed by casting the class to a PropertyManager and registering the listener
	// @ObservableListenerMethod and @ObserverFilterMethod tells the BeanManager to look for a method which will has the signature of a PropertyChangeListener and PropertyChangeFilter respectively
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

	public default void setUsername(String username) {
		username().set(username);
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
