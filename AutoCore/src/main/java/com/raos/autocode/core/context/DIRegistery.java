package com.raos.autocode.core.context;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * The dependancy registry
 * @author aditya
 * @date Dec. 17, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface DIRegistery {

	/**
	 * Gets a bean registered with a certain class.
	 * There should only be one object which is this class to use this method
	 * 
	 * @param <T> - Class type
	 * @param clazz - class parameter
	 * @return only object of this class
	 */
	<T> T getBean(Class<T> clazz);
	
	/**
	 * Gets a bean registered with a certain class and name.
	 * 
	 * @param <T> - Class type
	 * @param clazz - class parameter
	 * @param name - name of bean
	 * @return only object of this class
	 */
	<T> T getBean(Class<T> clazz, String name);

	/**
	 * Gets a bean registered with a name.
	 * 
	 * @param <T> - Class type
	 * @param name - name of bean
	 * @return only object of this class
	 */
	<T> T getBean(String name);
	
	/**
	 * Puts a bean into the registered with a name.
	 * 
	 * @param name - name of bean
	 * @param object - object to be kept in the registry
	 * @return only object of this class
	 */
	<T> T putBean(String name, T object);
	

	/**
	 * Puts a bean into the registry.
	 * 
	 * @param object - object to be kept in the registry
	 * @return only object of this class
	 */
	<T> T putBean(T object);
}
