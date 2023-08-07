package com.raos.autocode.core.beans.property;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

// Acts as a manager for the properties
// Root for dependency injection for beans
public interface PropertyManager extends PropertyChangeListener<Object>, PropertyChangeFilter<Object> {

	// Gets the property by name
	<T> Property<T> getProperty(String name);

	Set<Property<?>> getProperties();

	// Gets the value of the property
	@SuppressWarnings("unchecked")
	public default <T> T getValue(String name) {
		return (T) getProperty(name).get();
	}

	// Sets the value of property
	public default <T> void setValue(String name, T value) {
		getProperty(name).set(value);
	}

	// Checks equality between properties
	default boolean equals(PropertyManager other) {
		// If the other is null, return false
		if (other == null)
			return false;
		// Must have the same number of properties
		if (other.getProperties().size() == this.getProperties().size())
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
	// Registration of change listener
	public static <T> void registerChangeListener(Property<T> property, PropertyChangeListener<T> listener) {
		if (property instanceof ObservableProperty)
			((ObservableProperty<T>) property).getListeners().add(listener);
		else
			throw new IllegalArgumentException(
					"ERROR: Property is not observable. Consider using the @Observable annotation on the property (if in a bean) or an ObservableProperty instance");
	}

	public default void registerChangeListener(String name, PropertyChangeListener<?> listener) {
		registerChangeListener(Objects.requireNonNull(getProperty(name)), listener);
	}

	// deregistration of change listener
	public static <T> void deregisterChangeListener(Property<T> property, PropertyChangeListener<T> listener) {
		if (property instanceof ObservableProperty)
			((ObservableProperty<T>) property).getListeners().remove(listener);
		else
			throw new IllegalArgumentException(
					"ERROR: Property is not observable. Consider using the @Observable annotation on the property (if in a bean) or an ObservableProperty instance");
	}

	public default void deregisterChangeListener(String name, PropertyChangeListener<?> listener) {
		deregisterChangeListener(Objects.requireNonNull(getProperty(name)), listener);
	}

	// Registration of change filter
	public static <T> void registerChangeFilter(Property<T> property, PropertyChangeFilter<T> filter) {
		if (property instanceof ObservableProperty)
			((ObservableProperty<T>) property).getFilters().add(filter);
		else
			throw new IllegalArgumentException(
					"ERROR: Property is not observable. Consider using the @Observable annotation on the property (if in a bean) or an ObservableProperty instance");
	}

	public default void registerChangeFilter(String name, PropertyChangeFilter<?> filter) {
		registerChangeFilter(Objects.requireNonNull(getProperty(name)), filter);
	}

	// deregistration of change listener
	public static <T> void deregisterChangeFilter(Property<T> property, PropertyChangeFilter<T> filter) {
		if (property instanceof ObservableProperty)
			((ObservableProperty<T>) property).getFilters().remove(filter);
		else
			throw new IllegalArgumentException(
					"ERROR: Property is not observable. Consider using the @Observable annotation on the property (if in a bean) or an ObservableProperty instance");
	}

	public default void deregisterChangeFilter(String name, PropertyChangeFilter<?> filter) {
		deregisterChangeFilter(Objects.requireNonNull(getProperty(name)), filter);
	}

	// Do this in the proxy
	public static String toString(PropertyManager thiz) {
		return String.format("%s [ %s ]", thiz.getClass(), thiz.getProperties().stream()
				.map(p -> String.format("%s = %s", p.getName(), p.get())).collect(Collectors.joining(", ")));
	}

	// Do this in the proxy
	public static int hashCode(PropertyManager thiz) {
		return thiz.getProperties().stream().map(Property::getValue).collect(Collectors.toSet()).hashCode();
	}

	// Do this in the proxy
	public static boolean equals(PropertyManager thiz, PropertyManager other) {
		// Check size
		if (thiz.getProperties().size() != other.getProperties().size())
			return false;

		// Check properties
		for (Property<?> property : thiz.getProperties()) {
			if (other.getProperty(property.getName()) == null
					|| !Objects.equals(property, other.getProperty(property.getName())))
				return false;
		}
		return true;
	}
}