package com.raos.autocode.core.test;

import com.raos.autocode.core.annotation.beans.Bean;
import com.raos.autocode.core.annotation.beans.BeanProperty;
import com.raos.autocode.core.annotation.beans.Immutable;
import com.raos.autocode.core.annotation.beans.Observable;
import com.raos.autocode.core.annotation.beans.ObserverChangeClass;
import com.raos.autocode.core.annotation.beans.ObserverChangeMethod;
import com.raos.autocode.core.annotation.beans.ObserverFilterClass;
import com.raos.autocode.core.annotation.beans.ObserverFilterMethod;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyChangeListener;
import com.raos.autocode.core.beans.property.PropertyHandler;

@Bean
public interface StudentBean {

	@BeanProperty(nullable = true, required = true)
	@Immutable
	public Property<String> userName();

	@Observable
	@ObserverChangeMethod(methodName = "passwordChanged")
	@ObserverFilterMethod(methodName = "validatePassword")
	@BeanProperty(nullable = true, required = true)
	public Property<String> password();

	@Observable
	@BeanProperty(nullable = true)
	@ObserverChangeClass(listenerClass = AgeHandler.class)
	@ObserverFilterClass(filterClass = AgeHandler.class)
	public Property<Integer> age();

	
	public static class AgeHandler implements PropertyHandler<Integer> {

		@Override
		public void onChange(Property<Integer> property, Integer old, Integer newv) {
			System.out.println("");
		}

		@Override
		public boolean filter(Property<Integer> property, Integer newv) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	
	
	
	// Allowed listeners: Runnable, Consumer<Property<?>>, TriConsumer<Property<?>, ?, ?>
	public default void noArglistener() {
	}

	public default void oneArglistener(Property<?> property) {

	}

	public default void fullListener(Property<?> property, Object old, Object newv) {

	}

	// Allowed filters types: Predicate<?>, BiPredicate<Property<?>, ?>
	public default boolean passwordChanged(Object newv) {
		return false;
	}
	
	public default boolean propertyFilter(Property<?> property, Object newv) {
		// Return false to signal invalid parameter
		return false;
	}
}
