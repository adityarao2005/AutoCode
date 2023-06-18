package com.raos.autocode.core.algorithm;

import java.util.Map;

// An interface that represents the union-find algorithms for disjoint set
@Deprecated
public interface UnionFind<E> {

	// The maps required by disjoint set
	// Requires access to parent
	Map<E, E> parents();

	// Requires access to rank
	Map<E, Integer> ranks();

	// Requires access to size
	Map<E, Integer> sizes();

	// Finds the root of the set
	default E findSet(E v) {
		// If the parent is itself, this is the root node, we return it
		if (v.equals(parents().get(v)))
			return v;
		
		// Otherwise, find the root node and set the parent to lazily access it
		return parents().replace(v, findSet(parents().get(v)));
	}

	// Creates a new set
	default void makeSet(E e) {
		// Make itself a new root node
		parents().put(e, e);
		sizes().put(e, 1);
		ranks().put(e, 0);
	}
	
	

	// Find the roots and add the roots
	default void unionRank(E a, E b) {
		// Finds the root nodes
		a = findSet(a);
		b = findSet(b);
		// If the roots are not equal to each other
		if (!a.equals(b)) {
			// Check the ranks, we want the greater to be on the left
			if (ranks().get(a) < ranks().get(b)) {
				E temp = a;
				a = b;
				b = temp;
			}

			// Make the one with the smaller root be parent to the one with larger root
			parents().replace(b, a);
			// Set sizes
			sizes().replace(a, sizes().get(a) + sizes().get(b));

			// Set ranks
			if (ranks().get(b) == ranks().get(a)) {
				ranks().replace(a, ranks().get(a) + 1);
			}
		}
	}

	// Find the roots and add the roots
	default void unionSize(E a, E b) {
		// Finds the root nodes
		a = findSet(a);
		b = findSet(b);
		// If the roots are not equal to each other
		if (!a.equals(b)) {
			// Check the sizes, we want the greater to be on the left
			if (sizes().get(a) < sizes().get(b)) {
				E temp = a;
				a = b;
				b = temp;
			}

			// Make the one with the smaller root be parent to the one with larger root
			parents().replace(b, a);
			// Set sizes
			sizes().replace(a, sizes().get(a) + sizes().get(b));

			// Set ranks
			if (ranks().get(b) == ranks().get(a)) {
				ranks().replace(a, ranks().get(a) + 1);
			}
		}
	}

}
