package com.raos.autocode.core.test.application;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.application.Application;
import com.raos.autocode.core.application.ConsoleApplication;
import com.raos.autocode.core.context.ApplicationContext;

// test class
@ClassPreamble(author = "Aditya Rao", date = "Dec. 22, 2023")
public class TestApplication extends ConsoleApplication {

	@Override
	public void init(ApplicationContext context) throws Exception {
		// Call the super method
		super.init(context);

		// TEst the context and dependency injection
		Tester.testRegistryPutMethod(context);

	}

	// Start method
	@Override
	public void start(ApplicationContext context) throws Exception {

		System.out.println("Test within TestApplication.start method: ");
		Tester.testRegistryGetMethods(context);
		// Run program from Runner
		Tester.run();

	}

	public static void main(String[] args) {
		Application.run(TestApplication.class, args);
	}

}
