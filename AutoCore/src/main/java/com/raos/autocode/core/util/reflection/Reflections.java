package com.raos.autocode.core.util.reflection;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * This class acts as a helper class to make class reflection easier
 * This class provides a variety of methods to access certain portions of a class
 * 
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "8/25/2023")
public final class Reflections {

	private Reflections() {
	}
	
	/**
	 * Creates a new class spier
	 * 
	 * @param spied - to be spied on
	 * @return
	 */
	public static ClassSpy classSpy(Class<?> spied) {
		return new ClassSpy(spied);
	}
	
	/**
	 * 
	 */
	public static PackageSpy packageSpy(String packageName) {
		return new PackageSpy(packageName);
	}
	
	/**
	 * 
	 */
	public static ObjectSpy objectSpy(Object spied) {
		return new ObjectSpy(spied);
	}
	
	/**
	 * 
	 */
	public static VariableSpy fieldSpy(Field field) {
		return new VariableSpy(field);
	}
	

	/**
	 * 
	 */
	public static MethodSpy methodSpy(Method method) {
		return new MethodSpy(method);
	}
	
}
