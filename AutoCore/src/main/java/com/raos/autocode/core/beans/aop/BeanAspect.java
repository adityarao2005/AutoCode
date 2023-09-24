package com.raos.autocode.core.beans.aop;

import java.util.Map;

import com.raos.autocode.core.Aspect;
import com.raos.autocode.core.PostProcessor;
import com.raos.autocode.core.PreProcessor;
import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.util.ObjectUtil;

/**
 * Represents a generic aspect for bean processing.
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface BeanAspect extends Aspect {

	/**
	 * Adds PreProcessor only if it is a BeanPreProcessor
	 */
	@Override
	default void addPreProcessor(PreProcessor processor) {
		// Make it require the type of BeanPreProcessor
		ObjectUtil.requireType(processor, BeanPreProcessor.class);

		// Add via overloaded method
		addPreProcessor((BeanPreProcessor) processor);
	}

	/**
	 * Adds Processor only if it is a BeanPostProcessor
	 */
	@Override
	default void addPostProcessor(PostProcessor processor) {
		// Make it require the type of BeanPostProcessor
		ObjectUtil.requireType(processor, BeanPostProcessor.class);

		// Add via overloaded method
		addPostProcessor((BeanPostProcessor) processor);
	}

	/**
	 * Add bean pre processor
	 * 
	 * @param processor
	 */
	void addPreProcessor(BeanPreProcessor processor);

	/**
	 * Add bean post processor
	 * 
	 * @param processor
	 */
	void addPostProcessor(BeanPostProcessor processor);

	/**
	 * Returns Aspect wide resources based on the bean type passed
	 * 
	 * @param bean
	 * @return
	 */
	Map<String, Object> getResources(Class<?> bean);

}
