package com.raos.autocode.core.set;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

class MultiSet extends NumberSet {
	// Sub sets
	private Set<SingletonSet> subsets;

	// Private no-arg constructor
	private MultiSet() {
		subsets = new TreeSet<>(Comparator.comparing(SingletonSet::getLowerBound));
	}

	// Constructors
	public MultiSet(SingletonSet set1, SingletonSet set2) {
		this();
		subsets.add(set1);
		subsets.add(set2);
	}

	// Singleton set + multiset
	public MultiSet(SingletonSet set1, MultiSet set2) {
		this();
		subsets.add(set1);
		subsets.addAll(set2.subsets);

	}

	// singleton set + multiset
	public MultiSet(MultiSet set1, SingletonSet set2) {
		this(set2, set1);

	}

	//
	public MultiSet(MultiSet set1, MultiSet set2) {
		this();
		subsets.addAll(set1.subsets);
		subsets.addAll(set2.subsets);

	}

	// Checks if the subsets are empty
	@Override
	public boolean isEmpty() {
		// Check if all the subsets are empty
		for (NumberSet set : subsets)
			if (!set.isEmpty())
				return false;

		// return true if they are
		return true;
	}

	@Override
	public boolean contains(Double t) {

		// Check if any of the subsets contain the value
		for (NumberSet set : subsets)
			if (set.contains(t))
				return true;

		// Otherwise return false
		return false;
	}

}
