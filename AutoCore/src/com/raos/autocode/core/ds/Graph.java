package com.raos.autocode.core.ds;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.raos.autocode.core.algorithm.Traversable;
import com.raos.autocode.core.util.Pair.Twin;

// Graph theory introduced
// Uses graph theory concepts to outline a graph object
// Makes sure that I do not require nodes
// Creates the graph theory Data Structure and Incorporates the Algorithm
// This will be in the DS package since it contains the DS portion of it
@Deprecated
// It will implement the algorithm portion
public class Graph<E> implements Traversable<E> {

	// Many to many relationship being used
	private Map<E, List<E>> adjList;
	private Map<Twin<E>, Integer> weights;

	// Constructs the graph object
	public Graph() {
		// Constructs the adjacency list and weighting
		adjList = new HashMap<>();
		weights = new HashMap<>();
	}

	public void addPath(E from, E to, int weight) {
		addPath(from, to, true, weight);
	}

	// Adds a path, could be bidirectional, with a weighting associated
	public void addPath(E from, E to, boolean bidirectional, int weight) {
		// Adds the path from "from" node to "to" node and incorporates weighting in it

		// Create the list that holds the values that are adjacent
		adjList.putIfAbsent(from, new LinkedList<>());

		// Adds a path from "from" to "to"
		adjList.get(from).add(to);
		// Adds associated weighting
		weights.put(new Twin<>(from, to), weight);

		// If it is directed, then add the values in the backward direction
		if (bidirectional) {

			// Create the list that holds the values that are adjacent
			adjList.putIfAbsent(to, new LinkedList<>());

			// Adds a path from "to" to "from"
			adjList.get(to).add(from);
			// Adds associated weighting
			weights.put(new Twin<>(to, from), weight);
		}

	}

	// We check whether there is a path connecting "from" to "to"
	// If it is directed, we must check in both directions
	public boolean containsPath(E from, E to, boolean bidirectional) {

		// Check if the node is the stranded
		if (!adjList.containsKey(from))
			return false;

		// Check if the other node is stranded if it is directed
		if (bidirectional && !adjList.containsKey(to))
			return false;

		// Check if there is a path from "from" to "to"
		if (!adjList.get(from).contains(to))
			return false;

		// Check if there is a path from "to" to "from" if it is directed
		if (bidirectional && !adjList.get(to).contains(from))
			return false;

		// Base case, return true
		return true;
	}

	// Gets weighting
	// No need for directed or undirected, that will make this messy
	public int getWeighting(E from, E to) {
		// Returns the weights
		return weights.get(new Twin<>(from, to));
	}

	// Sets weighting
	// Bi directional required
	public void setWeighting(E from, E to, boolean bidirectional, int weight) {

		// Sets weighting in the "from" to "to" direction
		weights.replace(new Twin<>(from, to), weight);

		// Sets it in the other direction only if bidirectional
		if (bidirectional) {
			weights.replace(new Twin<>(to, from), weight);
		}
	}

	// Removes a path, could be both ways or just one
	public void removePath(E from, E to, boolean bidirectional) {
		// First we must check whether that there is a path in the first place
		if (adjList.containsKey(from)) {
			// If there is a path from "from" to "to"
			if (adjList.get(from).contains(to)) {
				// Remove the path
				adjList.get(from).remove(to);
				// Remove the weighting associated
				weights.remove(new Twin<>(from, to));
			}
		}

		// Check if it is directed
		if (bidirectional && adjList.containsKey(to)) {

			// Check whether there is a path
			if (adjList.get(to).contains(from)) {
				// Remove the path
				adjList.get(to).remove(from);
				// Remove the weighting associated
				weights.remove(new Twin<>(to, from));

			}
		}
	}

	// Creates an immutable adjacency list to represent the graph object
	public Map<E, List<E>> getAdjacencyList() {
		// Create a separate mapping
		Map<E, List<E>> list = new HashMap<>();

		// Add all of the mapped values and make sure it is immutable
		adjList.forEach((k, v) -> list.put(k, Collections.unmodifiableList(v)));

		// Return an immutable map
		return Collections.unmodifiableMap(list);
	}

	// Returns an immutable weightings map
	public Map<Twin<E>, Integer> getWeightings() {
		return Collections.unmodifiableMap(weights);
	}

	// Hashcode, kinda useless
	@Override
	public int hashCode() {
		return Objects.hash(adjList, weights);
	}

	// Checks for equality, only two conditions and both must be satisfied
	// 1) the adjacency lists must be the same
	// 2) the weightings must be the same
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Graph<E> other = (Graph<E>) obj;
		return Objects.equals(adjList, other.adjList) && Objects.equals(weights, other.weights);
	}

}