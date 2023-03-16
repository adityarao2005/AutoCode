package com.raos.autocode.core.algorithm;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

import com.raos.autocode.core.ds.Pair;

// Graph theory algorithms
// Graph theory DS will implement these
public interface Traversable<E> {

	// Returns the adjacency list
	Map<E, List<E>> getAdjacencyList();

	// Returns the weightings
	Map<Pair<E, E>, Integer> getWeightings();

	// ***** BFS Applications

	// Shortest Path
	public default Deque<E> findShortestPath(E start, E end) {
		// Check if we are asking for the same thing
		if (start == end || start.equals(end))
			return new LinkedList<>();

		Map<E, Deque<E>> paths = findShortestPaths(start);
		
		// Return the smallest path
		return paths.get(end);
	}
	
	// Shortest Paths
	public default Map<E, Deque<E>> findShortestPaths(E start) {

		// Create our path stack map
		Map<E, Deque<E>> paths = new HashMap<>();
		// Create our distance map
		Map<E, Integer> distance = new HashMap<>();

		// Put stacks into the adjacency list
		getAdjacencyList().keySet().forEach(e -> {
			distance.put(e, Integer.MAX_VALUE);
			paths.put(e, new ArrayDeque<>());
		});

		// Keep the distance from the start and end to be 0
		distance.put(start, 0);

		// Create a priority queue
		// That sorts edges based on the weight
		PriorityQueue<Pair<E, E>> queue = new PriorityQueue<Pair<E, E>>(
				Comparator.comparing(e -> Objects.requireNonNullElse(getWeightings().get(e), 0)));

		// Add the zeroth edge
		queue.add(new Pair<>(null, start));

		// While the queue of edges are not empty
		while (!queue.isEmpty()) {

			// Get the edge
			Pair<E, E> oPair = queue.poll();
			// Get the last endpoint
			E current = oPair.getSecond();

			// Go through all edges relating to that endpoint
			for (E next : getAdjacencyList().get(current)) {
				// Create the edge
				Pair<E, E> cPair = new Pair<>(current, next);

				// Calculate the total distance
				int calc = distance.get(current) + getWeightings().get(cPair);

				// The calculated distance is smaller
				if (distance.get(next) > calc) {

					// Replace the distance
					distance.replace(next, calc);
					// Add a new path
					paths.replace(next, new ArrayDeque<>(paths.get(current)));
					paths.get(next).add(current);

					// Add edge into queue
					queue.add(cPair);
				}
			}
		}

		for (E e : paths.keySet()) {
			paths.get(e).add(e);
		}
		
		// Return the shortest paths
		return paths;
	}

	// Minimum Spanning Tree

	// ****** DFS Applications

	// Cycle Detection

	// Path Finding

	// Topological Sorting

}
