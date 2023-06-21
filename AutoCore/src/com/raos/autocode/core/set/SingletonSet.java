package com.raos.autocode.core.set;

import java.util.Objects;

// Singleton set
abstract class SingletonSet extends NumberSet {
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
	public boolean contains(Double number) {

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

	// Hashcode
	@Override
	public int hashCode() {
		return Objects.hash(lowerBound, lowerInclusive, upperBound, upperInclusive);
	}

	// Equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		SingletonSet other = (SingletonSet) obj;
		
		return Objects.equals(lowerBound, other.lowerBound) && lowerInclusive == other.lowerInclusive
				&& Objects.equals(upperBound, other.upperBound) && upperInclusive == other.upperInclusive;
	}

}
