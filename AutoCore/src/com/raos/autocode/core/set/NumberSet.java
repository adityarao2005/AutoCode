package com.raos.autocode.core.set;

import java.math.BigDecimal;

import com.raos.autocode.core.annotations.ToDo;
import com.raos.autocode.core.util.NumberComparator;

// TODO: UNFINISHED
@ToDo(description = "Implement these methods soon using set theory and the MultiSet and SingletonSet provided", methods = {
		"union", "intersection", "except", "disjoint" })
public abstract class NumberSet<T extends Number & Comparable<T>> implements ComparableSet<T, NumberSet<T>> {

	// Constants
	public static final FiniteLimit ZERO = new FiniteLimit(0);
	public static final FiniteLimit ONE = new FiniteLimit(1);
	public static final Limit POS_INFINITY = new Limit() {

		// Always will be greater
		@Override
		public int compareTo(Limit o) {
			// If its infinity then they are the same
			if (this == o)
				return 0;

			// Always greater
			return 1;
		}

		// ToString method
		@Override
		public String toString() {
			return "Infinity";
		}
	};
	public static final Limit NEG_INFINITY = new Limit() {

		// Always will be less
		@Override
		public int compareTo(Limit o) {
			// If its negative infinity then they are the same
			if (this == o)
				return 0;

			// Always less
			return -1;
		}

		@Override
		public String toString() {
			return "-Infinity";
		}
	};

	// Limit which holds a value
	public static abstract class Limit implements Comparable<Limit> {

		// Create an abstract toString method which serves as its identity
		public abstract String toString();

		// Check for equality by checking the string values
		public boolean equals(Limit other) {
			return this.toString().equals(other.toString());
		}

		// Compares if less than
		public boolean isLessThan(Limit other) {
			return compareTo(other) < 0;
		}

		// Compares if greater
		public boolean isGreaterThan(Limit other) {
			return compareTo(other) > 0;
		}
	}

	// Numerical/Finite limit is a limit which holds a number
	public static final class FiniteLimit extends Limit {

		// Numerical Value
		private Number value;

		// Constructor
		public FiniteLimit(Number value) {
			this.value = value;
		}

		// Gets the numerical value
		public Number getValue() {
			return value;
		}

		// ToString method
		@Override
		public String toString() {
			return value.toString();
		}

		// Compare method
		@Override
		public int compareTo(Limit o) {
			// Obey the infinity law
			if (o.equals(POS_INFINITY))
				return -1;

			if (o.equals(NEG_INFINITY))
				return 1;

			// Compare the values
			FiniteLimit lim = (FiniteLimit) o;

			// Compare the values
			return NumberComparator.compare(value, lim.value);
		}

		// Returns true if compare to is true
		@Override
		public boolean equals(Limit o) {
			return compareTo(o) == 0;
		}

	}

	// Get number set in all reals
	public static NumberSet<Double> forNumberInReals() {
		return new SingletonSet<>(POS_INFINITY, NEG_INFINITY, false, false);
	}

	// Get number set in all rational
	public static NumberSet<BigDecimal> forNumberInRationals() {
		return new SingletonSet<>(POS_INFINITY, NEG_INFINITY, false, false);
	}

	// Get number set in all integers
	public static NumberSet<Integer> forNumberInInteger() {
		return new SingletonSet<>(POS_INFINITY, NEG_INFINITY, false, false);
	}

	// Get number set in all whole numbers
	public static NumberSet<Integer> forNumberInWhole() {
		return new SingletonSet<>(ZERO, NEG_INFINITY, true, false);
	}

	// Get number set in all whole naturals
	public static NumberSet<Integer> forNumberInNatural() {
		return new SingletonSet<>(ZERO, NEG_INFINITY, false, false);
	}

	@Override
	public NumberSet<T> union(NumberSet<T> other) {
		// Case 1: Singleset and Singleset
		if (this instanceof SingletonSet && other instanceof SingletonSet) {
			//
			SingletonSet<T> set1 = (SingletonSet<T>) this;
			SingletonSet<T> set2 = (SingletonSet<T>) other;

			// If both their ranges intersect, then create another singleton set
			// Otherwise create a multiset
			// set1 = [a,b]
			// set2 = [c,d]
			//
			// new singletonset(e, f, (a E set2) ? : , )

			NumberSet<T> value;
			if (set1.getLowerBound().isGreaterThan(set2.getUpperBound())
					|| set1.getUpperBound().isLessThan(set2.getLowerBound())) {
				
				
				
			} else {
				value = new SingletonSet<>(ONE, NEG_INFINITY, isEmpty(), isEmpty());
			}

		}

		return null;
	}

	@Override
	public NumberSet<T> intersection(NumberSet<T> other) {
		return null;
	}

	@Override
	public NumberSet<T> except(NumberSet<T> other) {
		return null;
	}

	@Override
	public boolean disjoint(NumberSet<T> other) {
		return false;
	}
}
