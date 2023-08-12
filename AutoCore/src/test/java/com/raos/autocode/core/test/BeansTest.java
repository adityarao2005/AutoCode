package com.raos.autocode.core.test;

import static org.junit.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.raos.autocode.core.beans.BeanFactory;
import com.raos.autocode.core.beans.BeansInstantationException;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.util.MapBuilder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeansTest {
	static BeanFactory beanFactory = new BeanFactory();
	private static StudentBean bean;
	private static Logger logger = Logger.getLogger(BeansTest.class.getName());

	void testInit() throws BeansInstantationException {

		bean = beanFactory.createBean(StudentBean.class);

		bean.username().setValue("adityarao");
		bean.password().setValue("weakpassword");
		bean.age().setValue(10);
	}

	void testCtorInit() throws BeansInstantationException {

		bean = beanFactory.createBean(StudentBean.class, MapBuilder.<String, Object>create()
				.addEntry("username", "adityarao").addEntry("password", "weakpassword").addEntry("age", 10).build());
	}

	@Order(1)
	@Test
	void testObserverChangeAndFilterClass() throws BeansInstantationException {
		logger.info("Starting test: testObserverChangeAndFilterClass()");

		testInit();

		bean.age().setValue(-1);

		System.out.println(bean);
		assertEquals(bean.age().get(), 10);

		logger.info("Ending test: testObserverChangeAndFilterClass()");

	}

	@Order(2)
	@Test
	void testObserverChangeAndFilterMethod() throws BeansInstantationException {
		logger.info("Starting test: testObserverChangeAndFilterMethod()");

		testCtorInit();

		bean.password().setValue("weak");

		System.out.println(bean);
		assertEquals(bean.password().getValue(), "weakpassword");

		logger.info("Ending test: testObserverChangeAndFilterMethod()");

	}

	@Order(4)
	@Test
	void testDestructor() {
		logger.info("Starting test: testDestructor()");

		beanFactory.destroyBean(bean);

		assertEquals(beanFactory.checkDestroyed(bean), true);

		assertThrows(NullPointerException.class, bean::age);

		logger.info("Ending test: testDestructor()");
	}

	@Order(5)
	@Test
	void testDestroyAll() {
		logger.info("Starting test: testDestroyAll()");

		beanFactory.close();

		logger.info("Ending test: testDestroyAll()");
	}

	@Order(3)
	@Test
	void testStackTrace() {
		bean.printStack();

	}
}
