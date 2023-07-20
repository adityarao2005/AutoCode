package com.raos.autocode.core.util;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.raos.autocode.core.util.functional.Callback;

public final class ExceptionUtil {
	private ExceptionUtil() {

	}

	public static interface TRunnable {
		void run() throws Exception;
	}

	public static interface TSupplier<V> extends Callable<V> {
	}

	public static interface TConsumer<V> {
		void accept(V v) throws Exception;
	}

	public static interface TFunction<R, V> extends Callback<R, V> {

	}

	// Throw silently
	public static <T> Supplier<T> throwSilently(TSupplier<T> supplier) {
		return () -> {
			try {
				return supplier.call();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T> Runnable throwSilently(TRunnable supplier) {
		return () -> {
			try {
				supplier.run();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T> Consumer<T> throwSilently(TConsumer<T> supplier) {
		return (v) -> {
			try {
				supplier.accept(v);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T, V> Function<T, V> throwSilently(TFunction<T, V> supplier) {
		return (t) -> {
			try {
				return supplier.call(t);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}
}
