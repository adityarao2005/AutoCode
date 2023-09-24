package com.raos.autocode.core.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.raos.autocode.core.util.functional.Callback;

/**
 * Utility class for exception handling
 * @author aditya
 *
 */
public final class ExceptionUtil {
	private ExceptionUtil() {

	}

	public static interface TRunnable {
		void run() throws Throwable;
	}

	public static interface TSupplier<V> {

		V get() throws Throwable;
	}

	public static interface TConsumer<V> {
		void accept(V v) throws Throwable;
	}

	public static interface TFunction<R, V> extends Callback<R, V> {

	}

	public static interface TBiPredicate<R, V> {
		boolean test(R r, V v) throws Throwable;
	}

	public static interface TTriConsumer<R, V, E> {
		void accept(R r, V v, E e) throws Throwable;
	}

	// Throw silently
	public static <T> Supplier<T> throwSilently(TSupplier<T> supplier) {
		return () -> {
			try {
				return supplier.get();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T> T throwSilentlyAndGet(TSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	// Throw silently
	public static <T> Runnable throwSilently(TRunnable supplier) {
		return () -> {
			try {
				supplier.run();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T> Consumer<T> throwSilently(TConsumer<T> supplier) {
		return (v) -> {
			try {
				supplier.accept(v);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T, V> Function<T, V> throwSilently(TFunction<T, V> supplier) {
		return (t) -> {
			try {
				return supplier.call(t);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	// Throw silently
	public static <T, V> V throwSilentlyAndGet(TFunction<T, V> supplier, T arg) {
		try {
			return supplier.call(arg);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}


}
