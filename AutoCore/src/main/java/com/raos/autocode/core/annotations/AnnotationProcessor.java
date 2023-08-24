package com.raos.autocode.core.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashMap;
import java.util.Map;

// Will process annotations and get attributes from them
// Will search for annotation inheritance and include inherited properties as well (only annotations which have their Target value as more than jut ANNOTATION_TYPE)
// 
// 
public class AnnotationProcessor {
	// Callbacks
	private final Map<Class<? extends Annotation>, ProcessorCallback<? extends Annotation>> callbacks = new HashMap<>();

	public AnnotationProcessor() {
	}

	// Processes the annotation element
	public void process(AnnotatedElement element) {
		for (Annotation annotation : element.getAnnotations())
			if (callbacks.containsKey(annotation.annotationType()))
				invokeCallback(element, annotation);
	}

	// Registers a callback
	public <T extends Annotation> void registerCallback(Class<T> type, ProcessorCallback<T> callback) {
		callbacks.put(type, callback);
	}

	// Invokes the callback
	@SuppressWarnings("unchecked")
	private <T extends Annotation> void invokeCallback(AnnotatedElement element, Annotation annotation) {
		Class<T> type = (Class<T>) annotation.annotationType();

		((ProcessorCallback<T>) callbacks.get(type)).process(element, type.cast(annotation));
	}

	// Processes annotation
	public static interface ProcessorCallback<T extends Annotation> {

		void process(AnnotatedElement element, T annotation);
	}
	
}
