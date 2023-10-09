package com.raos.autocode.core.beans.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.context.CDISupport;
import com.raos.autocode.core.util.ReflectionUtil;

/**
 * Default bean aspect used by engine
 * 
 * @author aditya
 * @date Sep. 29, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Sep. 29, 2023")
public class DefaultBeanAspect implements BeanAspect {
	// Pre processors list
	private List<BeanPreProcessor> preProcessors;
	// Post processors list
	private List<BeanPostProcessor> postProcessors;
	// Bean property generator
	private BeanPropertyGenerator generator;
	// Private resource scope for class
	private Map<Class<?>, Map<String, Object>> resources;

	/**
	 * Creates a new bean aspect
	 */
	public DefaultBeanAspect() {
		preProcessors = new LinkedList<>();
		postProcessors = new LinkedList<>();
		generator = null; // TODO: Create a default generator elsewhere
		resources = new HashMap<>();
	}

	// Add to list
	@Override
	public void addPreProcessor(BeanPreProcessor processor) {
		preProcessors.add(processor);
	}

	// Add to list
	@Override
	public void addPostProcessor(BeanPostProcessor processor) {
		postProcessors.add(processor);
	}

	// Set the generator
	@Override
	public void setBeanPropertyGenerator(BeanPropertyGenerator generator) {
		this.generator = generator;
	}

	// Gets the resources
	@Override
	public Map<String, Object> getResources(Class<?> bean) {
		resources.putIfAbsent(bean, new HashMap<>());
		return resources.get(bean);
	}

	@Override
	public BeanPropertyGenerator getBeanPropertyGenerator() {
		return generator;
	}

	@Override
	public Map<Method, Map<String, String>> applyPreProcessors(Class<?> beanClass) {
		// method properties
		Map<Method, Map<String, String>> metadata = ReflectionUtil.getMethodsStreamed(beanClass, false)
				// Filters when the method identifies as a property method
				.filter(PropertyManager::isPropertyMethod)
				// Collects the methods into a map
				.collect(HashMap::new, (map, method) -> {
					// Puts the method with properties into the map
					map.put(method, new HashMap<>());
				}, HashMap::putAll);

		// Apply the preprocessors
		for (BeanPreProcessor preprocessor : preProcessors)
			for (Method key : metadata.keySet())
				metadata.get(key).putAll(preprocessor.process(beanClass, key));

		// Return the metadata
		return metadata;
	}

	@Override
	public void applyPostProcessors(Object bean, CDISupport support) {
		// Go through all the post processors
		for (BeanPostProcessor processor : postProcessors)
			processor.process(bean, support);
	}

}
