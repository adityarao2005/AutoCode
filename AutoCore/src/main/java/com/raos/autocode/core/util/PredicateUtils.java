package com.raos.autocode.core.util;

import java.util.function.Predicate;

public class PredicateUtils {

	public static <T> Predicate<T> and(Predicate<T> first, Predicate<T> second) {
		return first.and(second);
	}
	
	public static <T> Predicate<T> or(Predicate<T> first, Predicate<T> second) {
		return first.or(second);
	}
}
