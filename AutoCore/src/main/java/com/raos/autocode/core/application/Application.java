package com.raos.autocode.core.application;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.context.ApplicationContext;

/**
 * Represents the main application interface to be used with this framework. All
 * concrete classes which extends this interface must have a 0 arg constructor.
 * 
 * @author aditya
 * @date Dec. 20, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 20, 2023")
public interface Application {

	/**
	 * Start the application given the context. Main application logic should begin here
	 * 
	 * @param context
	 */
	void start(ApplicationContext context) throws Exception;

	/**
	 * Initialize the context. Creation of any resources should be done here
	 * 
	 * @param context
	 */
	default void init(ApplicationContext context) throws Exception {
	}

	/**
	 * Stop the application. Any closing of application resources should be done here
	 * 
	 * @param context
	 */
	default void stop(ApplicationContext context) throws Exception {
	}

	/**
	 * Run the application
	 * 
	 * @param <T>
	 * @param runnerClass
	 */
	public static <T extends Application> void run(Class<T> runnerClass, String... args) throws ApplicationException {
		// Application process
		try {
			// Create an instance of the application
			T application = runnerClass.getConstructor().newInstance();

			// Create the application context
			ApplicationContext context = ApplicationContext.getApplicationContext();

			// Initialize the application
			application.init(context);
			// Start the application
			application.start(context);
			// Stop the application
			application.stop(context);

			// Close the context
			context.close();

		} catch (Exception e) {
			throw new ApplicationException("Cannot instantiate the application class: " + runnerClass.getName(), e);
		}

	}

}
