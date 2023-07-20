package com.raos.autocode.core.beans.property;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import com.raos.autocode.core.util.MultiMap;

// Acts as a manager for the properties
// Root for dependency injection for beans
public interface PropertyManager extends PropertyChangeListener<Object>, PropertyChangeFilter<Object> {

	// Gets the property by name
	<T> Property<T> getProperty(String name);

	// Gets the value of the property
	@SuppressWarnings("unchecked")
	public default <T> T getValue(String name) {
		return (T) getProperty(name).get();
	}

	// Sets the value of property
	public default <T> void setValue(String name, T value) {
		getProperty(name).set(value);
	}

	Property<?>[] getProperties();

	// Checks equality between properties
	default boolean equals(PropertyManager other) {
		// If the other is null, return false
		if (other == null)
			return false;
		// Must have the same number of properties
		if (other.getProperties().length == this.getProperties().length)
			return false;

		// Go through all the properties
		for (Property<?> prop : getProperties())
			// Must have the same property
			if (other.getProperty(prop.getName()) == null)
				return false;
			// Value of property must be the same
			else if (!other.getProperty(prop.getName()).equals(prop))
				return false;

		// Equivalent properties
		return true;
	}

	// FOR OBSERVABLE PROPERTIES ONLY
	// Register change listener
	void registerChangeListener(Property<?> property, PropertyChangeListener<?> listener);

	public default void registerChangeListener(String name, PropertyChangeListener<?> listener) {
		registerChangeListener(Objects.requireNonNull(getProperty(name)), listener);
	}

	// Register change filter
	void registerChangeFilter(Property<?> property, PropertyChangeFilter<?> filter);

	public default void registerChangeFilter(String name, PropertyChangeFilter<?> filter) {
		registerChangeFilter(Objects.requireNonNull(getProperty(name)), filter);
	}

	// Listeners
	MultiMap<Property<?>, PropertyChangeListener<?>> getRegisteredListeners();

	// Filters
	MultiMap<Property<?>, PropertyChangeFilter<?>> getRegisteredFilters();

	// Do this in the proxy
	public static String toString(PropertyManager thiz) {
		return String.format("%s [ %s ]", thiz.getClass(), Arrays.stream(thiz.getProperties())
				.map(p -> String.format("%s = %s", p.getName(), p.get())).collect(Collectors.joining(", ")));
	}

}
