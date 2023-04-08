package com.raos.autocode.core.ds;

import java.util.HashMap;
import java.util.Map;

import com.raos.autocode.core.algorithm.UnionFind;

public class DisjointSet<E> implements UnionFind<E> {
	private Map<E, E> parents = new HashMap<>();
	private Map<E, Integer> ranks = new HashMap<>();
	private Map<E, Integer> sizes = new HashMap<>();

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
