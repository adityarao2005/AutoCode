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
import com.raos.autocode.core.beans.property.flavour.OVProperty;
import com.raos.autocode.core.beans.property.flavour.ObservableProperty;
import com.raos.autocode.core.beans.property.flavour.ReadOnlyProperty;
import com.raos.autocode.core.beans.property.flavour.ValidatableProperty;

@Bean
public interface Verbose_Student {

	@BeanProperty
	@ReadOnly
	ReadOnlyProperty<String> username();

	// Interceptable property
	@BeanProperty
	@NonNull
	@Validatable
	ValidatableProperty<String> password();

	@BeanProperty
	@NonNull
	@Observable
	ObservableProperty<Integer> age();

	// Observable and interceptable property

	@BeanProperty
	@Observable
	@Validatable
	OVProperty<Integer> avg();

	// List property invocation

	@BeanProperty
	@ListProperty
	ListProperty<String> classes();

	// Map property invocation
	@BeanProperty
	@MapProperty
	MapProperty<String, Object> properties();

	@Init
	public default void init() {
		System.out.println("Init method called");
	}

	@Destructor
	public default void destroy() {
		System.out.println("Destroy method called");
	}
}
