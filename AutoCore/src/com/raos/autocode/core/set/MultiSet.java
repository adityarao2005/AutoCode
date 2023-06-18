package com.raos.autocode.core.set;

import java.util.ArrayList;
import java.util.List;


class MultiSet<T extends Number & Comparable<T>> extends NumberSet<T> {
	// Sub sets
	private List<NumberSet<T>> subsets;

	private MultiSet() {
		subsets = new ArrayList<>();
	}

	//
	public MultiSet(SingletonSet<T> set1, SingletonSet<T> set2) {
		this();
		subsets.add(set1);
		subsets.add(set2);
	}

	//
	public MultiSet(SingletonSet<T> set1, MultiSet<T> set2) {
		this();
		subsets.add(set1);

		recursiveSetAddition(set2);
	}

	//
	public MultiSet(MultiSet<T> set1, SingletonSet<T> set2) {
		this();

		recursiveSetAddition(set1);
		subsets.add(set2);
	}

	//
	public MultiSet(MultiSet<T> set1, MultiSet<T> set2) {
		this();
		recursiveSetAddition(set1);
		recursiveSetAddition(set2);

	}

	private void recursiveSetAddition(MultiSet<T> set) {
		for (NumberSet<T> subset : set.subsets) {
			if (subset instanceof MultiSet)
				recursiveSetAddition((MultiSet<T>) subset);
			else
				subsets.add(subset);
		}
	}

	// Checks if the subsets are empty
	@Override
	public boolean isEmpty() {
		// Check if all the subsets are empty
		for (NumberSet<T> set : subsets)
			if (!set.isEmpty())
				return false;

		// return true if they are
		return true;
	}

	@Override
	public boolean contains(T t) {

		// Check if any of the subsets contain the value
		for (NumberSet<T> set : subsets)
			if (set.contains(t))
				return true;

		// Otherwise return false
		return false;
	}

}
