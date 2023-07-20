package com.raos.autocode.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

// Classpath Searching tool
public final class ClasspathUtil {
	// Loading classes is expensive
	private static Set<Class<?>> classes;

	// Make sure we cant initialize it
	private ClasspathUtil() {
		throw new IllegalAccessError("Not supposed to be accessed");
	}

	// Check for instantiability
	public static boolean isInstantiable(Class<?> clz) {
		if (clz.isPrimitive() || Modifier.isAbstract(clz.getModifiers()) || clz.isInterface() || clz.isArray()) {
			return false;
		}

		return true;
	}

	// Find all concrete implementations of a classs
	@SuppressWarnings("unchecked")
	public static <T> Set<Class<? extends T>> findAllConcreteInstances(Class<T> clazz) throws IOException {

		// Get all classes which are instantiable
		return scanClassPath().stream().filter(e -> clazz.isAssignableFrom(e) && isInstantiable(e))
				.map(e -> (Class<? extends T>) e).collect(Collectors.toSet());
	}

	// Scans the class path
	public static Set<Class<?>> scanClassPath() throws IOException {
		// Lazy load
		if (classes != null)
			return classes;

		// Make the set unmodifiable
		return classes = Collections.unmodifiableSet(
				findClassesInPath(Thread.currentThread().getContextClassLoader().getResource(""), null));
	}

	// Scans classpath
	// Recursive Function
	private static Set<Class<?>> findClassesInPath(URL root, String basePackage) throws IOException {

		// Get an input stream to the root and read the directory list
		try (InputStream stream = root.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			// Read the list and filter for classes and directories
			return reader.lines()
					// Filter for classes and directories
					.filter(line -> line.endsWith(".class") || new File(root.getPath(), line).isDirectory())
					// Then turn into classes
					.flatMap(line -> {

						// If the line is a class file, load the class and merge
						if (line.endsWith(".class"))
							return Arrays.asList(getClass(line, basePackage)).stream();
						else {

							// Else
							// Perform recursive call and scan the sub directories
							return ExceptionUtil
									.throwSilently(() -> findClassesInPath(new URL(root.toString() + "/" + line),
											basePackage == null ? line : basePackage + "." + line).stream())
									// Get the value
									.get();
						}

						// Turn stream into a set
					}).collect(Collectors.toSet());
		}
	}

	// Getting the class from classname
	private static Class<?> getClass(String className, String packageName) {
		// Get the class name
		return ExceptionUtil
				.throwSilently(
						() -> Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.'))))
				.get();
	}
}
