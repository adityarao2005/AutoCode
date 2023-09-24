package com.raos.autocode.core.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Gets all annotations on an annotatable element
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public final class AnnotationScraper {

	private AnnotationScraper() {
	}

	/**
	 * Scrape an annotated element of its annotations and return them to user
	 * 
	 * @param annotated - element to be scraped
	 * @param meta      - if true, then will pass meta annotations as well
	 * @return a stream of annotations
	 */
	public static Stream<Annotation> scrape(AnnotatedElement annotated, boolean meta) {
		// if not meta return the annotations the regular way
		if (!meta) {
			return Arrays.stream(annotated.getAnnotations());
			// Else
		} else {

			// Total annotations
			Set<Annotation> annotationSet = new HashSet<>();

			// Get annotations
			for (Annotation annotation : annotated.getAnnotations()) {
				// Add annotation to annotation set
				if (annotationSet.add(annotation))
					scrapeMeta(annotation, annotationSet);
			}

			// Return annotation stream
			return annotationSet.stream();
		}

	}

	// Scrapes annotation of its meta annotations
	private static void scrapeMeta(Annotation annotation, Set<Annotation> annotationSet) {
		// Get type of annotation
		Class<?> annotationType = annotation.annotationType();

		// Get annotations in annotation type
		for (Annotation extended : annotationType.getAnnotations())
			// If it doesnt already exist then add
			if (annotationSet.add(extended))
				// Scrape the extended annotation
				scrapeMeta(extended, annotationSet);

	}
}
