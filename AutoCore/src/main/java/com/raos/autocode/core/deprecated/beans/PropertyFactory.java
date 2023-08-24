package com.raos.autocode.core.beans;

import java.util.Objects;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;

@ClassPreamble(author = "Aditya Rao", date = "8/15/2023")
public final class PropertyFactory {

	private PropertyFactory() {
	}

	public static <T> Property<T> createProperty(String propertyName, Class<T> propertyClass) {
		return new PropertyImpl<>(propertyName, null, propertyClass, false, false);
	}

	public static <T> ObservableProperty<T> createObservableProperty(String propertyName, Class<T> propertyClass) {
		return new ObservablePropertyImpl<>(propertyName, null, propertyClass, false, false);
	}

	public static boolean deepEquals(Property<?> first, Property<?> second) {
		return Objects.equals(first.getBean(), second.getBean()) && Objects.equals(first.getName(), second.getName())
				&& Objects.equals(first.getType(), second.getType())
				&& Objects.equals(first.getValue(), second.getValue());
	}

	public static int deepHashCode(Property<?> first) {
		return Objects.hash(first.getBean(), first.getName(), first.getType(), first.getValue());
	}
}
