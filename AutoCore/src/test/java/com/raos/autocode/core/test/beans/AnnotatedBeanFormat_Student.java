package com.raos.autocode.core.test.beans;

import java.util.List;
import java.util.Map;

import com.raos.autocode.core.annotations.beans.Bean;
import com.raos.autocode.core.annotations.beans.Destructor;
import com.raos.autocode.core.annotations.beans.Init;
import com.raos.autocode.core.annotations.beans.property.Observable;
import com.raos.autocode.core.annotations.beans.property.BeanProperty;
import com.raos.autocode.core.annotations.beans.property.NonNull;
import com.raos.autocode.core.annotations.beans.property.ReadOnly;
import com.raos.autocode.core.annotations.beans.property.Validatable;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.flavour.ObservableProperty;

@Bean
public interface AnnotatedBeanFormat_Student {

	@BeanProperty
	@ReadOnly
	Property<String> username();

	// Interceptable property
	@BeanProperty
	@NonNull
	@Validatable
	Property<String> password();

	@BeanProperty
	@NonNull
	@Observable
	Property<Integer> age();

	// Observable and interceptable property

	@BeanProperty
	@Observable
	@Validatable
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
