package com.raos.autocode.core.algorithm;

import java.util.Map;

public interface UnionFind<E> {

	Map<E, E> parents();

	Map<E, Integer> ranks();

	Map<E, Integer> sizes();

	// Finds the root of the set
	default E findSet(E v) {
		if (v == parents().get(v))
			return v;
		return parents().replace(v, findSet(parents().get(v)));
	}

	// Creates a new set
	default void makeSet(E e) {
		parents().put(e, e);
		sizes().put(e, 1);
		ranks().put(e, 0);
	}

	// Find the roots and add the roots
	default void unionRank(E a, E b) {
		a = findSet(a);
		b = findSet(b);
		if (!a.equals(b)) {
			if (ranks().get(a) < ranks().get(b)) {
				E temp = a;
				a = b;
				b = temp;
			}

			parents().replace(b, a);
			sizes().replace(a, sizes().get(a) + sizes().get(b));

			if (ranks().get(b) == ranks().get(a)) {
				ranks().replace(a, ranks().get(a) + 1);
			}
		}
	}

	// Find the roots and add the roots
	default void unionSize(E a, E b) {
		a = findSet(a);
		b = findSet(b);
		if (!a.equals(b)) {
			if (sizes().get(a) < sizes().get(b)) {
				E temp = a;
				a = b;
				b = temp;
			}

			parents().replace(b, a);
			sizes().replace(a, sizes().get(a) + sizes().get(b));

			if (ranks().get(b) == ranks().get(a)) {
				ranks().replace(a, ranks().get(a) + 1);
			}
		}
	}

}
