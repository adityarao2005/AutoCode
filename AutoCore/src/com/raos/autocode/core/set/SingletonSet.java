package com.raos.autocode.core.set;

// Singleton set
class SingletonSet<T extends Number & Comparable<T>> extends NumberSet<T> {
	// Upper bound limit
	private Limit upperBound;
	// Lower bound limit
	private Limit lowerBound;

	// Lower bound inclusive
	private boolean lowerInclusive;
	// Upper bound inclusive
	private boolean upperInclusive;

	// Creates a bounded set
	public SingletonSet(Limit upperBound, Limit lowerBound, boolean lowerInclusive, boolean upperInclusive) {
		// Set the values
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.lowerInclusive = lowerInclusive;
		this.upperInclusive = upperInclusive;
	}

	public Limit getUpperBound() {
		return upperBound;
	}

	public Limit getLowerBound() {
		return lowerBound;
	}

	public boolean isLowerInclusive() {
		return lowerInclusive;
	}

	public boolean isUpperInclusive() {
		return upperInclusive;
	}

	// Check if the values are empty
	@Override
	public boolean isEmpty() {
		return lowerInclusive == false && upperInclusive == false && lowerBound.equals(upperBound);
	}

	@Override
	public boolean contains(T number) {

		FiniteLimit value = new FiniteLimit(number);

		// If the number is above the upper bound or the value is the upper bound and we
		// are excluding the upper bound, return false
		if (value.isGreaterThan(upperBound) || (value.equals(upperBound) && !upperInclusive)) {
			return false;
		}

		// If the number is lower than the lower bound or the value is the lower bound
		// and we are excluding the lower bound, return false
		if (value.isLessThan(lowerBound) || (value.equals(lowerBound) && !lowerInclusive)) {
			return false;
		}

		// Otherwise return true
		return true;
	}

}
