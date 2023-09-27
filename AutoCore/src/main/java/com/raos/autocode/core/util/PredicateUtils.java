package com.raos.autocode.core.util;

import java.util.function.Predicate;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Predicate utils
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface PredicateUtils {

	/**
	 * And of two predicates
	 * 
	 * @param <T>
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> Predicate<T> and(Predicate<T> first, Predicate<T> second) {
		return first.and(second);
	}

	/**
	 * Or of two predicates
	 * 
	 * @param <T>
	 * @param first
	 * @param second
	 * @return
	 */
	public static <T> Predicate<T> or(Predicate<T> first, Predicate<T> second) {
		return first.or(second);
	}
}
