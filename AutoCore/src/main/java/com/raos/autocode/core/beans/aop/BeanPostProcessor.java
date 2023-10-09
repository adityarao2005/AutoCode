package com.raos.autocode.core.beans.aop;

import com.raos.autocode.core.PostProcessor;
import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.context.CDISupport;

/**
 * Processes bean after construction
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface BeanPostProcessor extends PostProcessor {

	/**
	 * Gets the aspect
	 * @return
	 */
	BeanAspect getAspect();
	
	/**
	 * Add post processing control after construction along with CDI support
	 * @param bean
	 */
	void process(Object bean, CDISupport cdi);
}
