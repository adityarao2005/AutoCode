package com.raos.autocode.core.test.beans;

import java.util.List;
import java.util.Map;

import com.raos.autocode.core.annotation.beans.Bean;
import com.raos.autocode.core.annotation.beans.Destructor;
import com.raos.autocode.core.annotation.beans.Init;
import com.raos.autocode.core.annotation.beans.observable.Observable;
import com.raos.autocode.core.annotation.beans.property.BeanProperty;
import com.raos.autocode.core.annotation.beans.property.NonNull;
import com.raos.autocode.core.annotation.beans.property.ReadOnly;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;

@Bean
public interface AnnotatedBeanFormat_Student {

	@BeanProperty
	@ReadOnly
	Property<String> username();

	// Interceptable property
	@BeanProperty
	@NonNull
	@Interceptable
	Property<String> password();

	@BeanProperty
	@NonNull
	@Observable
	Property<Integer> age();

	// Observable and interceptable property

	@BeanProperty
	@Observable
	@Interceptable
	Property<Integer> avg();

	// List property invocation

	@ListProperty
	Property<List<String>> classes();

	// Map property invocation

	@MapProperty
	Property<Map<String, Object>> properties();

	@Init
	public default void init() {
		System.out.println("Init method called");
	}

	@Destructor
	public default void destroy() {
		System.out.println("Destroy method called");
	}
}
