package com.raos.autocode.core.property;

import java.util.Properties;

public interface PropertyMap {

	void registerProperty(String name, Class<?> clazz);

	<T> Property<T> getProperty(String name);

	Properties getProperties();

	<T> T getValue(String name);

	<T> void setValue(String name, T value);

}
