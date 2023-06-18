package com.raos.autocode.core.set;

public interface ComparableSet<T extends Comparable<T>, K extends ComparableSet<T, K>> {

	// regular set operation: set.addAll(other)
	K union(K other);

	// regular set operation: set.retainAll(other)
	K intersection(K other);

	// regular set operation: set.removeAll(other)
	K except(K other);
	
	// regular set operation: Collections.disjoint(set, other)
	boolean disjoint(K other);
	
	// regular set operation: set.isEmpty()
	boolean isEmpty();
	
	// Check if the set contains the value
	boolean contains(T t);
}
