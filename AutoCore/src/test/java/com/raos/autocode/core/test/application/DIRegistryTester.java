package com.raos.autocode.core.test.application;

import java.util.Optional;
import java.util.concurrent.Executors;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.annotations.TestCase;
import com.raos.autocode.core.context.ApplicationContext;
import com.raos.autocode.core.context.DIRegistry;

@ClassPreamble(author = "Aditya Rao", date = "Dec. 22, 2023")
public class DIRegistryTester {

	public static void run() {
		// Get the context
		System.out.println("Test within TestApplication.run method: ");
		ApplicationContext context = ApplicationContext.getApplicationContext();
		// Test from another method
		DIRegistryTester.testRegistryGetMethods(context);
		DIRegistryTester.testRegistryRemoveMethods(context);
		DIRegistryTester.testRegistryGetMethods(context);
	}

	@TestCase("Test the put method")
	public static void testRegistryPutMethod(ApplicationContext context) {

		// Access the registry
		System.out.println("Test within TestApplication.put method: ");
		DIRegistry registry = context.getRegistry();

		// Get the put string bean
		registry.putBean("cool string", "Hello world");
		// Get the put string bean
		registry.putBean("coolest string", "I\'m cool");
		// Get the put string bean
		registry.putBean("coolest 1st string", "I\'m coolest");

		// Put with regular stuff
		Executors.newCachedThreadPool().execute(() -> {
			registry.putBean(Optional.of("Cooler String"));
		});
	}

	@TestCase("Test the get method")
	public static void testRegistryGetMethods(ApplicationContext context) {
		// Get the registry and log
		System.out.println("Test within TestApplication.get method: ");
		DIRegistry registry = context.getRegistry();

		// Get the value
		System.out.println("Value (By name) (\'Cooler string\'): " + registry.getBean("Cooler string"));
		System.out.println("Value (By name) (\'cool string\'): " + registry.getBean("cool string"));
		System.out.println("Value (By name and class) (\'coolest 1st string\' & String.class): " + registry.getBean(String.class, "coolest 1st string"));
		System.out.println("Default Value (By class) (Optional.class): " + registry.getBean(Optional.class));
		System.out.println();
		System.out.println();
	}
	
	@TestCase("Test the delete methods")
	public static void testRegistryRemoveMethods(ApplicationContext context) {
		// Get the registry and log
		System.out.println("Test within TestApplication.remove method: ");
		DIRegistry registry = context.getRegistry();
		
		// Get the registry
		registry.removeBean(Optional.class);
		registry.removeBean("cool string");
		registry.removeBean("coolest string", String.class);
		
		System.out.println();
		System.out.println();
	}
}
