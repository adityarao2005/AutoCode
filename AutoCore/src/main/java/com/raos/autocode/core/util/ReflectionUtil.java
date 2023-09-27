package com.raos.autocode.core.util;

import java.lang.StackWalker.StackFrame;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.util.functional.SetterUnaryOperator;

/**
 * Utilities for reflection handling
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface ReflectionUtil {

	/**
	 * Accessible setter operation as a unary operatior
	 * 
	 * @param <T>
	 * @return
	 */
	public static <T extends AccessibleObject> UnaryOperator<T> getAccessibleSetterOperator() {
		return (SetterUnaryOperator<T>) T::trySetAccessible;
	}

	/**
	 * Checks if name matches the other name
	 * 
	 * @param name
	 * @return
	 */
	public static Predicate<Member> nameMatches(String name) {
		return member -> member.getName().equals(name);
	}

	/**
	 * Checks if an annotation is present and returns a predicate
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @return
	 */
	public static <T extends Annotation> Predicate<AnnotatedElement> isAnnotationPresent(Class<T> annotationClass) {
		return member -> member.isAnnotationPresent(annotationClass);
	}

	/**
	 * Gets the fields as an array using enclosing class and private access
	 * 
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Field[] getFields(Class<?> enclosingClass, boolean privateAccess) {
		return !privateAccess ? enclosingClass.getFields() : enclosingClass.getDeclaredFields();
	}

	/**
	 * Gets the fields as an stream using enclosing class and private access
	 * 
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Stream<Field> getFieldsStreamed(Class<?> enclosingClass, boolean privateAccess) {
		return Arrays.stream(getFields(enclosingClass, privateAccess));
	}

	/**
	 * 
	 * /** Gets the fields as an array using enclosing class and private access with
	 * a specific annotation
	 * 
	 * @param enclosingClass
	 * @param privateAccess
	 * @param annotationClass
	 * @return
	 */
	public static Stream<Field> getFieldsWithAnnotation(Class<?> enclosingClass, boolean privateAccess,
			Class<? extends Annotation> annotationClass) {
		return getFieldsStreamed(enclosingClass, privateAccess).filter(m -> m.isAnnotationPresent(annotationClass));
	}

	/**
	 * Gets the methods as an array using enclosing class and private access
	 * 
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Method[] getMethods(Class<?> enclosingClass, boolean privateAccess) {
		return !privateAccess ? enclosingClass.getMethods() : enclosingClass.getDeclaredMethods();
	}

	/**
	 * Gets the methods as an stream using enclosing class and private access
	 * 
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Stream<Method> getMethodsStreamed(Class<?> enclosingClass, boolean privateAccess) {
		return Arrays.stream(getMethods(enclosingClass, privateAccess));
	}

	/**
	 * Return field by name as optional, if not found
	 * 
	 * @param name
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Optional<Field> getFieldByName(String name, Class<?> enclosingClass, boolean privateAccess) {
		return getFieldsStreamed(enclosingClass, privateAccess).filter(nameMatches(name)).findFirst();
	}

	/**
	 * Get any method which matches if any exists
	 * 
	 * @param name
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Optional<Method> getMethodByName(String name, Class<?> enclosingClass, boolean privateAccess) {
		return getMethodsStreamed(enclosingClass, privateAccess).filter(nameMatches(name)).findAny();
	}

	/**
	 * Get all method which matches if any exists
	 * 
	 * @param name
	 * @param enclosingClass
	 * @param privateAccess
	 * @return
	 */
	public static Stream<Method> getMethodsByName(String name, Class<?> enclosingClass, boolean privateAccess) {
		return getMethodsStreamed(enclosingClass, privateAccess).filter(nameMatches(name));
	}

	/**
	 * Get with methods with specific annotation as a stream
	 * 
	 * @param enclosingClass
	 * @param privateAccess
	 * @param annotationClass
	 * @return
	 */
	public static Stream<Method> getMethodsWithAnnotation(Class<?> enclosingClass, boolean privateAccess,
			Class<? extends Annotation> annotationClass) {
		return getMethodsStreamed(enclosingClass, privateAccess).filter(m -> m.isAnnotationPresent(annotationClass));
	}

	/**
	 * Gets caller using stack traces
	 * 
	 * @param levels
	 * @return
	 */
	public static Class<?> getCaller(int levels) {
		return StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
				.walk(str -> getCallerFrame(str, levels)).getDeclaringClass();
	}

	// Gets the caller frame for the getCaller method
	private static StackFrame getCallerFrame(Stream<StackFrame> stackframe, int levels) {
		// Gets stack frames
		StackFrame[] frames = stackframe.limit(levels).toArray(StackFrame[]::new);

		return frames[levels - 1];
	}

	// Checks the caller from a stack frame within a certain boundary
	private static boolean checkCallerFrame(Stream<StackFrame> stackframes, Class<?> caller) {
		return stackframes.map(StackFrame::getDeclaringClass).anyMatch(caller::equals);
	}

	/**
	 * Checks for the caller
	 * 
	 * @param caller
	 * @return
	 */
	public static boolean checkCaller(Class<?> caller) {
		return StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
				.walk(str -> checkCallerFrame(str, caller));
	}

	// Checks caller from max frames
	private static boolean checkCallerFrameMax(Stream<StackFrame> stackframes, int maxLevels, Class<?> caller) {
		return stackframes.limit(maxLevels).map(StackFrame::getDeclaringClass).anyMatch(caller::equals);
	}

	/**
	 * Checks caller based on max levels
	 * 
	 * @param caller
	 * @param maxLevels
	 * @return
	 */
	public static boolean checkCallerMax(Class<?> caller, int maxLevels) {
		return StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
				.walk(str -> checkCallerFrameMax(str, maxLevels, caller));
	}

	/**
	 * Gets annotations from annotated element
	 * 
	 * @param element
	 * @return
	 */
	public static Annotation[] getAnnotations(AnnotatedElement element) {
		return element.getAnnotations();
	}

	/**
	 * Gets annotations streamed from annotated element
	 * 
	 * @param element
	 * @return
	 */
	public static Stream<Annotation> getAnnotationsStreamed(AnnotatedElement element) {
		return Arrays.stream(getAnnotations(element));
	}

}
