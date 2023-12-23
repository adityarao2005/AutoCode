package com.raos.autocode.core.context;

import java.util.Map;
import java.util.Properties;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.IService;
import com.raos.autocode.core.beans.IServiceProvider;
import com.raos.autocode.core.design.Factory;
import com.raos.autocode.core.design.builder.Builder;

/**
 * Factory pattern
 * 	- Registered factories
 * Builder pattern
 *  - Registered Builders
 * Service locator
 *  - Registered Services
 * DI (IoC) Container
 * - Context registry
 * Dependency injection (constructor injection, parameter injection (if not required), setter injection of interface injection)
 * - Beans which depend on other values will be injected on startup
 * Environment Variables
 * 
 * @author aditya
 * @date Dec. 17, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface Context extends AutoCloseable {

	/**
	 * @return get the environmental variables
	 */
	Properties getEnvironmentalVariables();
	
	/**
	 * Return the registered factories
	 * @return
	 */
	Map<Class<?>, Factory<?>> getFactories();
	
	/**
	 * Get the factory with respect to a class
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T> Factory<T> getFactory(Class<T> clazz);
	
	/**
	 * Return the registered builders
	 * @return
	 */
	Map<Class<?>, Builder<?>> getBuilders();
	
	/**
	 * Get the builder with respect to a class
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T> Builder<T> getBuilders(Class<T> clazz);
	
	/**
	 * Return the registered service providers
	 * @return
	 */
	Map<Class<?>, IServiceProvider<?>> getServiceProviders();

	/**
	 * Get the service providers with respect to the class
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	<T extends IService> IServiceProvider<T> getServiceProviders(Class<T> clazz);
	
	/**
	 * Return the registry
	 * @return
	 */
	DIRegistery getRegistry();
	

}
