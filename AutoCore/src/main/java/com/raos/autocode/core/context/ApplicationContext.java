package com.raos.autocode.core.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.IServiceProvider;
import com.raos.autocode.core.context.DIRegistery.BeanDescriptor;
import com.raos.autocode.core.design.Factory;
import com.raos.autocode.core.design.builder.Builder;

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
	private List<Factory<?>> factories;
	// Builders
	private List<Builder<?>> builders;
	// Service Providers
	private List<IServiceProvider<?>> serviceProviders;
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
	 * Initialize and load the contexts into this container
	 * 
	 * @param contexts
	 */
	public void initContext(List<Context> contexts) {
		// Initialize the fields
		this.environmentalVariables = new Properties();
		this.factories = new ArrayList<>();
		this.builders = new ArrayList<>();
		this.serviceProviders = new ArrayList<>();
		// this.registry = new DIRegistry();

		for (Context context : contexts) {
			// Add all the values from the other contexts
			context.getEnvironmentalVariables().forEach(environmentalVariables::put);
			context.getFactories().forEach(factories::add);
			context.getBuilders().forEach(builders::add);
			context.getServiceProviders().forEach(serviceProviders::add);

			// Pour the values from the descriptor into this context
			for (BeanDescriptor descriptor : context.getRegistry()) {
				registry.putBean(descriptor.getName(), descriptor.getObject());
			}
		}
	}

	// Return environmental variables
	@Override
	public Properties getEnvironmentalVariables() {
		return environmentalVariables;
	}

	// Return factories
	@Override
	public List<Factory<?>> getFactories() {
		return factories;
	}

	// Return builders
	@Override
	public List<Builder<?>> getBuilders() {
		return builders;
	}

	// Return service providers
	@Override
	public List<IServiceProvider<?>> getServiceProviders() {
		return serviceProviders;
	}

	// Return registry
	@Override
	public DIRegistery getRegistry() {
		return registry;
	}
}
