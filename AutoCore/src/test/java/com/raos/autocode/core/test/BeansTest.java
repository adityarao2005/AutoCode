package com.raos.autocode.core.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.Test;

import com.raos.autocode.core.beans.BeanFactory;

class BeansTest {
	private static StudentBean bean;
	private static Logger logger = Logger.getLogger(BeansTest.class.getName());

	static void init() {

		if (bean == null) {
			BeanFactory beanFactory = new BeanFactory();
			bean = beanFactory.createBean(StudentBean.class);

			bean.username().setValue("adityarao");
			bean.password().setValue("weakpassword");
			bean.age().setValue(10);
		}
	}

	@Test
	void testObserverChangeAndFilterClass() {
		logger.info("Starting test: testObserverChangeAndFilterClass()");

		init();

		bean.age().setValue(-1);

		System.out.println(bean);
		assertEquals(bean.age().get(), 10);

		logger.info("Ending test: testObserverChangeAndFilterClass()");

	}

	@Test
	void testObserverChangeAndFilterMethod() {
		logger.info("Starting test: testObserverChangeAndFilterMethod()");

		init();

		bean.password().setValue("weak");

		System.out.println(bean);
		assertEquals(bean.password().getValue(), "weakpassword");

		logger.info("Ending test: testObserverChangeAndFilterMethod()");

	}

}
