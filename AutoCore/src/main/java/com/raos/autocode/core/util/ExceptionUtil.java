package com.raos.autocode.core.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.util.functional.Callback;

/**
 * Utility class for exception handling
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface ExceptionUtil {

	/**
	 * Throwing Runnable
	 * 
	 * @author aditya
	 *
	 */
	public static interface TRunnable {
		void run() throws Throwable;
	}

	/**
	 * Throwing Supplier
	 * 
	 * @author aditya
	 *
	 */
	public static interface TSupplier<V> {

		V get() throws Throwable;
	}


	/**
	 * Throwing Consumer
	 * 
	 * @author aditya
	 *
	 */
	public static interface TConsumer<V> {
		void accept(V v) throws Throwable;
	}


	/**
	 * Throwing Function
	 * 
	 * @author aditya
	 *
	 */
	public static interface TFunction<R, V> extends Callback<R, V> {

	}


	/**
	 * Throwing BiPredicate
	 * 
	 * @author aditya
	 *
	 */
	public static interface TBiPredicate<R, V> {
		boolean test(R r, V v) throws Throwable;
	}

	/**
	 * Execute the function and throw silently
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	public static <T> Supplier<T> throwSilently(TSupplier<T> supplier) {
		return () -> {
			try {
				return supplier.get();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	/**
	 * Execute and get result from the function and throw silently
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	public static <T> T throwSilentlyAndGet(TSupplier<T> supplier) {
		try {
			return supplier.get();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Execute the function and throw silently
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	public static <T> Runnable throwSilently(TRunnable supplier) {
		return () -> {
			try {
				supplier.run();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}


	/**
	 * Execute the function, get the result and throw silently
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	public static <T> Consumer<T> throwSilently(TConsumer<T> supplier) {
		return (v) -> {
			try {
				supplier.accept(v);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}


	/**
	 * Execute the function, get the result and throw silently
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	public static <T, V> Function<T, V> throwSilently(TFunction<T, V> supplier) {
		return (t) -> {
			try {
				return supplier.call(t);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}


	/**
	 * Execute the function, get the result and throw silently
	 * 
	 * @param <T>
	 * @param supplier
	 * @return
	 */
	public static <T, V> V throwSilentlyAndGet(TFunction<T, V> supplier, T arg) {
		try {
			return supplier.call(arg);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}


}
