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

import com.raos.autocode.core.util.functional.SetterUnaryOperator;

public class ReflectionUtil {

	public static <T extends AccessibleObject> UnaryOperator<T> getAccessibleSetterOperator() {
		return (SetterUnaryOperator<T>) T::trySetAccessible;
	}

	public static Predicate<Member> nameMatches(String name) {
		return member -> member.getName().equals(name);
	}

	public static <T extends Annotation> Predicate<AnnotatedElement> isAnnotationPresent(Class<T> annotationClass) {
		return member -> member.isAnnotationPresent(annotationClass);
	}

	public static Field[] getFields(Class<?> enclosingClass, boolean privateAccess) {
		return !privateAccess ? enclosingClass.getFields() : enclosingClass.getDeclaredFields();
	}

	public static Stream<Field> getFieldsStreamed(Class<?> enclosingClass, boolean privateAccess) {
		return Arrays.stream(getFields(enclosingClass, privateAccess));
	}

	public static Stream<Field> getFieldsWithAnnotation(Class<?> enclosingClass, boolean privateAccess,
			Class<? extends Annotation> annotationClass) {
		return getFieldsStreamed(enclosingClass, privateAccess).filter(m -> m.isAnnotationPresent(annotationClass));
	}

	public static Method[] getMethods(Class<?> enclosingClass, boolean privateAccess) {
		return !privateAccess ? enclosingClass.getMethods() : enclosingClass.getDeclaredMethods();
	}

	public static Stream<Method> getMethodsStreamed(Class<?> enclosingClass, boolean privateAccess) {
		return Arrays.stream(getMethods(enclosingClass, privateAccess));
	}

	// Return field as optional, if not found
	public static Optional<Field> getFieldByName(String name, Class<?> enclosingClass, boolean privateAccess) {
		return getFieldsStreamed(enclosingClass, privateAccess).filter(nameMatches(name)).findFirst();
	}

	// Get any method which matches if any exists
	public static Optional<Method> getMethodByName(String name, Class<?> enclosingClass, boolean privateAccess) {
		return getMethodsStreamed(enclosingClass, privateAccess).filter(nameMatches(name)).findAny();
	}

	// Get all method which matches if any exists
	public static Stream<Method> getMethodsByName(String name, Class<?> enclosingClass, boolean privateAccess) {
		return getMethodsStreamed(enclosingClass, privateAccess).filter(nameMatches(name));
	}

	// Get with methods
	public static Stream<Method> getMethodsWithAnnotation(Class<?> enclosingClass, boolean privateAccess,
			Class<? extends Annotation> annotationClass) {
		return getMethodsStreamed(enclosingClass, privateAccess).filter(m -> m.isAnnotationPresent(annotationClass));
	}

	public static Class<?> getCaller(int levels) {
		return StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
				.walk(str -> getCallerFrame(str, levels)).getDeclaringClass();
	}

	public static StackFrame getCallerFrame(Stream<StackFrame> stackframe, int levels) {
		StackFrame[] frames = stackframe.limit(levels).toArray(StackFrame[]::new);

		Arrays.asList(frames).forEach(System.out::println);
		return frames[levels - 1];
	}

	public static boolean checkCallerFrame(Stream<StackFrame> stackframes, Class<?> caller) {
		return stackframes.map(StackFrame::getDeclaringClass).anyMatch(caller::equals);
	}

	public static boolean checkCaller(Class<?> caller) {
		return StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
				.walk(str -> checkCallerFrame(str, caller));
	}

	public static boolean checkCallerFrameMax(Stream<StackFrame> stackframes, int maxLevels, Class<?> caller) {
		return stackframes.limit(maxLevels).map(StackFrame::getDeclaringClass).anyMatch(caller::equals);
	}

	public static boolean checkCallerMax(Class<?> caller, int maxLevels) {
		return StackWalker.getInstance(Set.of(StackWalker.Option.RETAIN_CLASS_REFERENCE))
				.walk(str -> checkCallerFrameMax(str, maxLevels, caller));
	}

	public static Annotation[] getAnnotations(AnnotatedElement element) {
		return element.getAnnotations();
	}

	public static Stream<Annotation> getAnnotationsStreamed(AnnotatedElement element) {
		return Arrays.stream(getAnnotations(element));
	}

	public static Stream<Annotation> getAnnotationsWithStreamed(AnnotatedElement element,
			Class<? extends Annotation> annotation) {
		return getAnnotationsStreamed(element).filter(t -> t.annotationType().isAnnotationPresent(annotation));
	}

	public static Annotation[] getAnnotationsWith(AnnotatedElement element, Class<? extends Annotation> annotation) {
		return getAnnotationsWithStreamed(element, annotation).toArray(Annotation[]::new);
	}
}
