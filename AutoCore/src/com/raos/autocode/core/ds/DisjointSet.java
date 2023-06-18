package com.raos.autocode.core.ds;

import java.util.HashMap;
import java.util.Map;

import com.raos.autocode.core.algorithm.UnionFind;

// Represents the Disjoint set data structure
// Houses a bunch of sets in the forms of trees and is used mostly for mst
@Deprecated
public class DisjointSet<E> implements UnionFind<E> {
	// Fields used to store the respective values
	private Map<E, E> parents = new HashMap<>();
	private Map<E, Integer> ranks = new HashMap<>();
	private Map<E, Integer> sizes = new HashMap<>();

	// Overrides the methods to return the fields
	@Override
	public Map<E, E> parents() {
		return parents;
	}

	@Override
	public Map<E, Integer> ranks() {
		return ranks;
	}

	@Override
	public Map<E, Integer> sizes() {
		return sizes;
	}

}
