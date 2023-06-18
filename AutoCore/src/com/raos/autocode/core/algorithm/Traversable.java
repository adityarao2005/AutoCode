package com.raos.autocode.core.algorithm;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import com.raos.autocode.core.ds.DisjointSet;
import com.raos.autocode.core.ds.Graph;
import com.raos.autocode.core.util.Pair.Twin;

// Graph theory algorithms
// Graph theory DS will implement these
@Deprecated
public interface Traversable<E> {

	// Returns the adjacency list
	Map<E, List<E>> getAdjacencyList();

	// Returns the weightings
	Map<Twin<E>, Integer> getWeightings();

	// Gets the edges of the graph
	default List<Twin<E>> getEdges() {
		return getWeightings().entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).distinct()
				.collect(Collectors.toList());
	}

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
		PriorityQueue<Twin<E>> queue = new PriorityQueue<Twin<E>>(
				Comparator.comparing(e -> Objects.requireNonNullElse(getWeightings().get(e), 0)));

		// Add the zeroth edge
		queue.add(new Twin<>(null, start));

		// While the queue of edges are not empty
		while (!queue.isEmpty()) {

			// Get the edge
			Twin<E> oPair = queue.poll();
			// Get the last endpoint
			E current = oPair.getSecond();

			// Go through all edges relating to that endpoint
			for (E next : getAdjacencyList().get(current)) {
				// Create the edge
				Twin<E> cPair = new Twin<>(current, next);

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
	// Uses Krustals algorithm to create the MST
	// Uses Disjoint Set Data Structure to speed up performance
	// Returns a Graph instance
	default Graph<E> createMST() {
		// Creates a new instance of the disjoint set
		DisjointSet<E> set = new DisjointSet<>();

		// Creates a new tree in the form of the graph class
		Graph<E> mst = new Graph<>();

		// Make our individual disjoint sets from the edges
		for (E node : getAdjacencyList().keySet())
			set.makeSet(node);

		// Get all the edges by sorting the weightings map and returning all the keys
		List<Twin<E>> edges = getEdges();

		// Sort all the edges from least to greatest
		Collections.sort(edges, Comparator.comparing(getWeightings()::get));

		// For each edge
		for (Twin<E> e : edges) {
			// Check if they create a cycle by using the union-find algorithm
			if (set.findSet(e.getFirst()) != set.findSet(e.getSecond())) {

				// If not
				// Add the edge and merge the two sets
				mst.addPath(e.getFirst(), e.getSecond(), getWeightings().get(e));
				set.unionRank(e.getFirst(), e.getSecond());
			}
		}

		// Return the mst
		return mst;
	}

	// ****** DFS Applications
	// Cycle Detection
	default boolean hasCycle() {
		return checkCycle(getAdjacencyList().keySet().iterator().next(), new HashSet<>());
	}

	private boolean checkCycle(E start, Set<E> stack) {
		// Checks whether we have already been here
		// Cycle detection!!!!
		if (!stack.add(start))
			return true;

		// Go through its adjacent nodes
		for (E next : getAdjacencyList().get(start))
			// Recursively repeat the process
			// If we have found the end
			// Return true
			if (checkCycle(next, stack))
				return true;

		// Otherwise, backtrack
		stack.remove(start);

		// Return false
		return false;
	}

	// Path Finding
	default boolean findPath(E start, E end, ArrayDeque<E> path, Set<E> visited) {
		// Checks whether we have already been here
		// Cycle detection!!!!

		if (!visited.add(start))
			return false;

		path.add(start);

		// Check if we are at the end
		if (start.equals(end)) {
			return true;
		}

		// Go through its adjacent nodes
		for (E next : getAdjacencyList().get(start))
			// Recursively repeat the process
			// If we have found the end
			// Return true
			if (findPath(next, end, path, visited))
				return true;

		// Recursively backtrack
		path.remove(start);

		return false;
	}
	
	// Used for topological sorting
	private void dfs(E start, Set<E> visited, List<E> ans) {
		// Mark this value as visited
		visited.add(start);
		
		// Go to the adjacent values
		for (E next : getAdjacencyList().get(start))
			// If they have not been visited
			if (!visited.contains(next))
				// Perform dfs on them
				dfs(next, visited, ans);
		
		// Add to the answer
		ans.add(start);
	}
	
	// Sorts the graph topologically
	// Uses DFS
	default List<E> topologicalSort() {
		// Create our visited and answer collections
		Set<E> visited = new HashSet<>();
		List<E> ans = new LinkedList<>();
		
		// Go through all the possible nodes
		for (E e : getAdjacencyList().keySet())
			// If they have not been visited, perform dfs
			if (!visited.contains(e))
				dfs(e, visited, ans);
		
		// Reverse the answers list so that it is in topological order
		Collections.reverse(ans);
		
		return ans;
	}

}