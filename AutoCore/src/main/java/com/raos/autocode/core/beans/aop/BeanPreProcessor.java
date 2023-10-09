package com.raos.autocode.core.beans.aop;

import java.lang.reflect.Method;
import java.util.Map;

import com.raos.autocode.core.PreProcessor;
import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Adds Pre-processing to beans, useful for bean construction and customization
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface BeanPreProcessor extends PreProcessor {

	/**
	 * Gets the aspect
	 * 
	 * @return
	 */
	BeanAspect getAspect();

	/**
	 * Uses reflection to unreflect the secrets of the class and creates a property
	 * based on secrets from the method
	 * 
	 * @param manager
	 * @param propertyMethod
	 * @return
	 */
	Map<String, String> process(Class<?> beanClass, Method propertyMethod);

}
