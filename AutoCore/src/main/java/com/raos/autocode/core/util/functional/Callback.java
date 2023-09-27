package com.raos.autocode.core.util.functional;

/**
 * Represents a callback function
 * @author aditya
 *
 * @param <R>
 * @param <V>
 */
@FunctionalInterface
public interface Callback<R, V> {

	V call(R arg) throws Throwable;
}
