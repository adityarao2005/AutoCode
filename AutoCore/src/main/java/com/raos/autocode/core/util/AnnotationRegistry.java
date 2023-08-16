package com.raos.autocode.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public class AnnotationRegistry {
	private Map<Class<? extends Annotation>, Annotation> registry;

	public AnnotationRegistry(AnnotatedElement element) {
		for (Annotation annotation : element.getAnnotations()) {
			registry.put(annotation.annotationType(), annotation);
		}
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotation) {
		return annotation.cast(registry.get(annotation));
	}


}
