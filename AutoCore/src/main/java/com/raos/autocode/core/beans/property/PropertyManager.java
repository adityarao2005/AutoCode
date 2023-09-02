package com.raos.autocode.core.beans.property;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * 
 * Acts as a manager for the properties of an "auto" bean
 * 
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
public interface PropertyManager {

	/**
	 * Gets the property by name
	 * 
	 * @param <T>  - Type of property
	 * @param name - name of property
	 * @return property if found or null
	 */
	<T> Property<T> getProperty(String name);

	/**
	 * Gets the property map
	 * 
	 * @return property map
	 */
	Map<String, Property<?>> getProperties();

	/**
	 * Gets the value of property by name
	 * 
	 * @param <T>  - Type of property
	 * @param name - name of property
	 * @return property value
	 */
	@SuppressWarnings("unchecked")
	public default <T> T getValue(String name) {
		return (T) getProperty(name).get();
	}

	/**
	 * Sets the value of property by name
	 * 
	 * @param <T>   - Type of property
	 * @param name  - name of property
	 * @param value - value to be set
	 */
	public default <T> void setValue(String name, T value) {
		getProperty(name).set(value);
	}

	/**
	 * Generates hashcode for property manager
	 * 
	 * @param thiz
	 * @return hashcode
	 */
	public static int hashCode(PropertyManager thiz) {
		return thiz.getProperties().values().stream().map(Property::getValue).collect(Collectors.toSet()).hashCode();
	}

	/**
	 * Checks equality for property manager
	 * 
	 * @param thiz
	 * @param other
	 * @return hashcode
	 */
	public static boolean equals(PropertyManager thiz, PropertyManager other) {
		// if both are null return true
		if (thiz == null && other == null)
			return true;

		// If one of them are null and the other isnt return false
		if (thiz == null || other == null)
			return false;

		// Check size
		if (thiz.getProperties().size() != other.getProperties().size())
			return false;

		// Check properties
		for (Property<?> property : thiz.getProperties().values()) {
			if (other.getProperty(property.getName()) == null)
				return false;
			if (!Objects.equals(property, other.getProperty(property.getName())))
				return false;
		}
		return true;
	}
}