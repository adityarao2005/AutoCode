package com.raos.autocode.core.util;

import java.math.BigInteger;
import java.util.Collection;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Basic math utilities that the regular java.util.Math class does not have
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public final class MathUtil {

	private MathUtil() {
	}

	/**
	 * Uses Euclids algorithm to find gcd
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int gcd(int a, int b) {
		// Get the maximum value
		int max = Math.max(a, b);
		// Get the minimum value
		int min = Math.min(a, b);

		// Error check if there is a negative number involved
		if (min < 0)
			throw new ArithmeticException("GCD only works for whole numbers, not negative values");

		// If the minimum value is 0 then return the max value as 0 = 0 * max
		if (min == 0)
			return max;

		// take the remainder and re-perform GCD until you reach 1
		return gcd(min, max % min);
	}

	/**
	 * Incorporates Euclid's GCD into LCM using LCM * GCD = a * b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int lcm(int a, int b) {
		return a * b / (gcd(a, b));
	}

	// Factorials
	private static BigInteger[] factorials = new BigInteger[255];

	// Initialize factorials
	static {
		factorials[0] = factorials[1] = BigInteger.ONE;
	}

	/**
	 * Calculates the factorials
	 * 
	 * @param num
	 * @return num!
	 */
	public static BigInteger factorial(int num) {

		// Error check if there is a negative number involved
		if (num < 0)
			throw new ArithmeticException("Factorials only work for whole numbers, not negative values");

		if (factorials[num] != null)
			return factorials[num];

		// n! = n * (n - 1)!
		// Memoize to prevent it running 2^n times
		return factorials[num] = (factorial(num - 1).multiply(BigInteger.valueOf(num)));
	}

	// For the minimum values

	/**
	 * Finds the minimum value of all integers in an array
	 * 
	 * @param array
	 * @return
	 */
	public static int min(int... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		int minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.min(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the minimum value of all doubles in an array
	 * 
	 * @param array
	 * @return
	 */
	public static double min(double... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		double minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.min(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the minimum value of all longs in an array
	 * 
	 * @param array
	 * @return
	 */
	public static long min(long... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		long minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.min(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the minimum value of all floats in an array
	 * 
	 * @param array
	 * @return
	 */
	public static float min(float... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		float minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.min(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the minimum value of all E in an array or collection
	 * 
	 * @param <E>
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <E extends Comparable<E>> E min(E... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		E minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			// Using comparator interface to test for greater or less
			if (minV.compareTo(array[i]) > 0)
				minV = array[i];

		return minV;
	}

	/**
	 * Finds the minimum value of all E in an array or collection
	 * 
	 * @param <E>
	 * @param collection
	 * @return
	 */
	public static <E extends Comparable<E>> E min(Collection<E> collection) {
		// Error check if the size is 0
		if (collection.isEmpty())
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest

		E minV = collection.iterator().next();

		// Find the minimum values
		for (E value : collection)
			// Using comparator interface to test for greater or less
			if (minV.compareTo(value) > 0)
				minV = value;

		return minV;
	}

	// For the maximum values

	/**
	 * Finds the maximum value of all integers in an array
	 * 
	 * @param array
	 * @return
	 */
	public static int max(int... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		int minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.max(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the maximum value of all doubles in an array
	 * 
	 * @param array
	 * @return
	 */
	public static double max(double... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		double minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.max(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the maximum value of all longs an array
	 * 
	 * @param array
	 * @return
	 */
	public static long max(long... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		long minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.max(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the maximum value of all float in an array
	 * 
	 * @param array
	 * @return
	 */
	public static float max(float... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		float minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			minV = Math.max(minV, array[i]);

		// Return the minimum value
		return minV;
	}

	/**
	 * Finds the maximum value of all E in an array
	 * 
	 * @param <E>
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <E extends Comparable<E>> E max(E... array) {
		// Error check if the size is 0
		if (array.length == 0)
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest
		E minV = array[0];

		// Find the minimum values
		for (int i = 1; i < array.length; i++)
			// Using comparator interface to test for greater or less
			if (minV.compareTo(array[i]) < 0)
				minV = array[i];

		return minV;
	}

	/**
	 * Finds the maximum value of all E in an array or collection
	 * 
	 * @param <E>
	 * @param collection
	 * @return
	 */
	public static <E extends Comparable<E>> E max(Collection<E> collection) {
		// Error check if the size is 0
		if (collection.isEmpty())
			throw new ArithmeticException("List must not be empty");

		// Set the first element as the smallest

		E minV = collection.iterator().next();

		// Find the minimum values
		for (E value : collection)
			// Using comparator interface to test for greater or less
			if (minV.compareTo(value) < 0)
				minV = value;

		return minV;
	}

	// For fibonnaci series
	private static BigInteger[] fibonnaci = new BigInteger[255];

	static {
		fibonnaci[0] = BigInteger.ZERO;
		fibonnaci[1] = fibonnaci[2] = BigInteger.ONE;
	}

	/**
	 * Calculates fibonnaci O(N) time on first run
	 * 
	 * @param num
	 * @return
	 */
	public static BigInteger fibonnaci(int num) {
		// Check if value is cached
		if (fibonnaci[num] != null)
			return fibonnaci[num];

		// Otherwise: fib(n) = fib(n - 1) + fib(n - 2)
		return fibonnaci(num - 1).add(fibonnaci(num - 2));
	}

}