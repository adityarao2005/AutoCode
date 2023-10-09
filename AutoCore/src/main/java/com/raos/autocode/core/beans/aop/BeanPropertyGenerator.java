package com.raos.autocode.core.beans.aop;

import java.util.Map;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.Property;

/**
 * Represents a property generator for a bean
 * 
 * @author adity
 * @date Sep. 29, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Sep. 29, 2023")
public interface BeanPropertyGenerator {

	/**
	 * Builds the property from the resources and the properties
	 * @param resources - read only resources
	 * @param properties - read only properties
	 * @return
	 */
	public Property<?> buildProperty(Map<String, Object> resources, Map<String, String> properties);
}
