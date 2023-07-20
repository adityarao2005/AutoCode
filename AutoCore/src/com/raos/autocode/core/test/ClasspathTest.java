package com.raos.autocode.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClasspathTest {

	public static void main(String[] args) throws IOException {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		URL dirs = loader.getResource("");

		findClassesInPath(dirs, null).forEach(System.out::println);
//		findAllClassesUsingClassLoader("com.raos.autocode.core.test").forEach(System.out::println);
	}

	public static Set<Class<?>> findClassesInPath(URL root, String basePackage) throws IOException {

		try (InputStream stream = root.openStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			return reader.lines().filter(line -> line.endsWith(".class") || new File(root.getPath(), line).isDirectory()).flatMap(line -> {

				System.out.println(basePackage + "." + line);

				if (line.endsWith(".class"))
					return Arrays.asList(getClass(line, basePackage)).stream();
				else {

					try {

						if (basePackage == null)
							return findClassesInPath(new URL(root.toString() + "/" + line), line).stream();
						else
							return findClassesInPath(new URL(root.toString() + "/" + line), basePackage + "." + line)
									.stream();
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			}).collect(Collectors.toSet());
		}
	}

	public static Set<Class<?>> findAllClassesUsingClassLoader(String packageName) {
		InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines().filter(line -> line.endsWith(".class")).map(line -> getClass(line, packageName))
				.collect(Collectors.toSet());
	}

	// Getting the class from classname
	private static Class<?> getClass(String className, String packageName) {
		try {
			System.out.println(packageName);
			System.out.println(className);

			return Class.forName(packageName + "." + className.substring(0, className.lastIndexOf('.')));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
