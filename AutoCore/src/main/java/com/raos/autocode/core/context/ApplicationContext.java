package com.raos.autocode.core.context;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.IService;
import com.raos.autocode.core.beans.IServiceProvider;
import com.raos.autocode.core.context.DIRegistery.BeanDescriptor;
import com.raos.autocode.core.design.Factory;
import com.raos.autocode.core.design.builder.Builder;
import com.raos.autocode.core.util.ExceptionUtil;

/**
 * Represents a context that is present application wide
 * 
 * @author aditya
 * @date Dec. 17, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public final class ApplicationContext implements Context {
	// Singleton application context
	private static volatile ApplicationContext applicationContext;
	// Properties of the Application Context
	// environmental variables
	private Properties environmentalVariables;
	// Factories
	private Map<Class<?>, Factory<?>> factories;
	// Builders
	private Map<Class<?>, Builder<?>> builders;
	// Service Providers
	private Map<Class<?>, IServiceProvider<?>> serviceProviders;
	// IoC Registry
	private DIRegistery registry;

	/**
	 * Returns the applicaiton context
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		// Check if null
		if (applicationContext == null) {
			// Synchronize the context
			synchronized (applicationContext) {
				// check if null
				if (applicationContext == null) {
					applicationContext = new ApplicationContext();
				}
			}
		}

		// return the application context
		return applicationContext;
	}

	/**
	 * Private constructor
	 */
	private ApplicationContext() {
		// Prevent reflection
		if (applicationContext == null)
			throw new RuntimeException("HahHa nice try");
	}

	/**
	 * Initialize and load the contexts into this container
	 * 
	 * @param contexts
	 */
	public void initContext(List<Context> contexts) throws Exception {
		// Initialize the fields
		// Add thread safety
		this.environmentalVariables = new Properties();
		this.factories = new ConcurrentHashMap<>();
		this.builders = new ConcurrentHashMap<>();
		this.serviceProviders = new ConcurrentHashMap<>();
		this.registry = new ApplicationRegistry();

		for (Context context : contexts) {
			// Add all the values from the other contexts
			context.getEnvironmentalVariables().forEach(environmentalVariables::put);
			context.getFactories().forEach(factories::put);
			context.getBuilders().forEach(builders::put);
			context.getServiceProviders().forEach(serviceProviders::put);

			// Pour the values from the descriptor into this context
			for (BeanDescriptor descriptor : context.getRegistry()) {
				registry.putBean(descriptor.getName(), descriptor.getObject());
			}

			// Close the context
			context.close();
		}
	}

	// Return environmental variables
	@Override
	public Properties getEnvironmentalVariables() {
		return environmentalVariables;
	}

	// Return factories
	@Override
	public Map<Class<?>, Factory<?>> getFactories() {
		return factories;
	}

	// Get the factory from the class
	@SuppressWarnings("unchecked")
	@Override
	public <T> Factory<T> getFactory(Class<T> clazz) {
		return (Factory<T>) builders.get(clazz);
	}

	// Return builders
	@Override
	public Map<Class<?>, Builder<?>> getBuilders() {
		return builders;
	}

	// Get the builder from the class
	@SuppressWarnings("unchecked")
	@Override
	public <T> Builder<T> getBuilders(Class<T> clazz) {
		return (Builder<T>) builders.get(clazz);
	}

	// Return service providers
	@Override
	public Map<Class<?>, IServiceProvider<?>> getServiceProviders() {
		return serviceProviders;
	}

	// Get the factory from the class
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IService> IServiceProvider<T> getServiceProviders(Class<T> clazz) {
		return (IServiceProvider<T>) serviceProviders.get(clazz);
	}

	// Return registry
	@Override
	public DIRegistery getRegistry() {
		return registry;
	}

	@Override
	public void close() throws Exception {
		// Clear the environmental variables
		environmentalVariables.clear();
		// Clear the factories
		factories.clear();
		// Clear the builders
		builders.clear();
		// Close the service providers
		serviceProviders.values().forEach(ExceptionUtil.<IServiceProvider<?>>throwSilently(IServiceProvider::close));
		// clear the service providers
		serviceProviders.clear();
		// Close the registry
		registry.close();
	}

}
