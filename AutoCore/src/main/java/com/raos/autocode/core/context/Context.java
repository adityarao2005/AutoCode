package com.raos.autocode.core.context;

import java.util.List;
import java.util.Properties;

import com.raos.autocode.core.annotations.ClassPreamble;
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
public interface Context {

	/**
	 * @return get the environmental variables
	 */
	Properties getEnvironmentalVariables();
	
	/**
	 * Return the registered factories
	 * @return
	 */
	List<Factory<?>> getFactories();
	
	/**
	 * Return the registered builders
	 * @return
	 */
	List<Builder<?>> getBuilders();
	
	/**
	 * Return the registered service providers
	 * @return
	 */
	List<IServiceProvider<?>> getServiceProviders();
	
	/**
	 * Return the registry
	 * @return
	 */
	DIRegistery getRegistry();
}
