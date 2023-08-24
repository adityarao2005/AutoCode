package com.raos.autocode.core.beans.property;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// Acts as a manager for the properties
// Root for dependency injection for beans
public interface PropertyManager {

	// Gets the property by name
	<T> Property<T> getProperty(String name);

	Map<String, Property<?>> getProperties();

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
		for (Property<?> prop : getProperties().values())
			// Must have the same property
			if (other.getProperty(prop.getName()) == null)
				return false;
			// Value of property must be the same
			else if (!other.getProperty(prop.getName()).equals(prop))
				return false;

		// Equivalent properties
		return true;
	}

	// Do this in the proxy
	public static int hashCode(PropertyManager thiz) {
		return thiz.getProperties().values().stream().map(Property::getValue).collect(Collectors.toSet()).hashCode();
	}

	// Do this in the proxy
	public static boolean equals(PropertyManager thiz, PropertyManager other) {
		// Check size
		if (thiz.getProperties().size() != other.getProperties().size())
			return false;

		// Check properties
		for (Property<?> property : thiz.getProperties().values()) {
			if (other.getProperty(property.getName()) == null
					|| !Objects.equals(property, other.getProperty(property.getName())))
				return false;
		}
		return true;
	}
}