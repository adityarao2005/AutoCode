package com.raos.autocode.core.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.raos.autocode.core.beans.BeanFactory;
import com.raos.autocode.core.util.MapBuilder;

class BeansTest {
	static BeanFactory beanFactory = new BeanFactory();
	private static StudentBean bean;
	private static Logger logger = Logger.getLogger(BeansTest.class.getName());

	void testInit() {

		bean = beanFactory.createBean(StudentBean.class);

		bean.username().setValue("adityarao");
		bean.password().setValue("weakpassword");
		bean.age().setValue(10);
	}

	void testCtorInit() {

		bean = beanFactory.createBean(StudentBean.class, MapBuilder.<String, Object>create()
				.addEntry("username", "adityarao").addEntry("password", "weakpassword").addEntry("age", 10).build());
	}

	@Test
	void testObserverChangeAndFilterClass() {
		logger.info("Starting test: testObserverChangeAndFilterClass()");

		testInit();

		bean.age().setValue(-1);

		System.out.println(bean);
		assertEquals(bean.age().get(), 10);

		logger.info("Ending test: testObserverChangeAndFilterClass()");

	}

	@Test
	void testObserverChangeAndFilterMethod() {
		logger.info("Starting test: testObserverChangeAndFilterMethod()");

		testCtorInit();

		bean.password().setValue("weak");

		System.out.println(bean);
		assertEquals(bean.password().getValue(), "weakpassword");

		logger.info("Ending test: testObserverChangeAndFilterMethod()");

	}

}
