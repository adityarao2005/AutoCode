package com.raos.autocode.core.test.beans;

import com.raos.autocode.core.annotations.beans.property.NonNull;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.flavour.OVProperty;
import com.raos.autocode.core.beans.property.flavour.ObservableProperty;
import com.raos.autocode.core.beans.property.flavour.ReadOnlyProperty;
import com.raos.autocode.core.beans.property.flavour.ValidatableProperty;

// Everything about the bean is return type based with the exception of a few annotations
// The structure for these are based on 3 categories
//  - Read Only properties (Properties which only the enclosing class can modify)
//  - Observable Properties (Properties which on change, can be listened onto)
//  - Interceptable Properties (Properties which when a change is requested, will validate the change)
// All these properties can overlap
// when they do overlap, they create combinations of properties
// 		- Read Only and Observable - Useful to monitor changes for properties
// 		- Observable and Interceptable - Useful to monitor and allow changes
// 		- Interceptable and Read Only - useful for validation on self views (not really practical as the class itself acts as a proxy)
// 		- and all of the above - Combines all characteristics of the following (again, not really practical as the class acts as the proxy)
// These are considered access modifiers
// now for the properties themselves
// they can be organized as regular properties or datastructure properties
// Regular properties have thier access modifiers along with Property<{type}>
//        examples: ReadOnlyProperty, ObservableProperty, InterceptableProperty, OIProperty, ROOProperty, ROIProperty, ROOIProperty
// These include the combinations of the properties
// For the data structure properties, they themselves implement the datastructure
// for example, instead of Property<List<String>>, you can use ListProperty<String>
// It also acts as a proxy to the internal list
// Some basic ones will be ListProperty, MapProperty and they will have their own custom modifiers attacted similar to the regular property

public interface SimpleBeanFormat_Student {

	ReadOnlyProperty<String> username();

	// Interceptable property
	@NonNull
	ValidatableProperty<String> password();

	@NonNull
	ObservableProperty<Integer> age();

	// Observable and interceptable property
	OVProperty<Integer> avg();

	// List property invocation
	ListProperty<String> classes();

	// Map property invocation
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
