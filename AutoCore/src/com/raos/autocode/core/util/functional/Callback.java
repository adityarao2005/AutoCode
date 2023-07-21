package com.raos.autocode.core.util.functional;

@FunctionalInterface
public interface Callback<R, V> {

	V call(R arg) throws Throwable;
}
