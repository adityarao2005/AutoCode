package com.raos.autocode.core.util;

import java.util.Objects;
import java.util.Set;

// This class is much needed
// Holds 2 objects that are somewhat related
@Deprecated
public class Pair<K, V> {

	// The values in a pair
	private K first;
	private V second;

	// Constructor for pair
	public Pair(K k, V v) {
		first = k;
		second = v;
	}

	// Getters and Setters
	public K getFirst() {
		return first;
	}

	public void setFirst(K first) {
		this.first = first;
	}

	public V getSecond() {
		return second;
	}

	public void setSecond(V second) {
		this.second = second;
	}

	// Generates hash code
	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	// Generates equals method
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		// Checks if the memory location is the same
		if (this == obj)
			return true;
		// Checks if the other object is null
		if (obj == null)
			return false;
		// Checks if they are the same class
		if (getClass() != obj.getClass())
			return false;

		// Casts it to this object
		Pair<K, V> other = (Pair<K, V>) obj;
		// Checks whether the first type and the second type are equal
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}

	// Creates a duplicate of the pair
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return new Pair<K, V>(first, second);
	}

	// Generates a string value
	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}

	// A twin class to describe two identical values
	public static class Twin<E> extends Pair<E, E> {

		public Twin(E k, E v) {
			super(k, v);
		}

		// Returns string value
		@Override
		public String toString() {
			return "Twin [first=" + getFirst() + ", second=" + getSecond() + "]";
		}

		// Hashed based on the sets
		@Override
		public int hashCode() {
			return Objects.hash(toSet());
		}
		
		// Returns a set
		public Set<E> toSet() {
			return Set.of(getFirst(), getSecond());
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			// Checks if the memory location is the same
			if (this == obj)
				return true;
			// Checks if the other object is null
			if (obj == null)
				return false;
			// Checks if they are the same class
			if (getClass() != obj.getClass())
				return false;
			
			Twin<E> other = (Twin<E>) obj;
			return toSet().equals(other.toSet());
		}

	}

}