package com.raos.autocode.core.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.raos.autocode.core.ds.Graph;
import com.raos.autocode.core.util.Pair.Twin;

// Tests the graph class
//
@Deprecated
public class GraphTest {

	public static void main(String[] args) {
		// Creating a graph tester
		Graph<Integer> graph = new Graph<Integer>();

		// Adding the graph paths
		graph.addPath(0, 1, 4);
		graph.addPath(0, 7, 8);
		graph.addPath(1, 7, 11);
		graph.addPath(1, 2, 8);
		graph.addPath(7, 8, 7);
		graph.addPath(7, 6, 1);
		graph.addPath(2, 8, 2);
		graph.addPath(2, 3, 7);
		graph.addPath(2, 5, 4);
		graph.addPath(8, 6, 6);
		graph.addPath(6, 5, 2);
		graph.addPath(6, 5, 2);
		graph.addPath(3, 5, 14);
		graph.addPath(3, 4, 9);
		graph.addPath(5, 4, 10);

		// Print the weight of each edge
		System.out.println("Adjacency Test");
		for (Twin<Integer> node : graph.getEdges()) {

			System.out.printf("%d <----> %d, weight: %d%n", node.getFirst(), node.getSecond(),
					graph.getWeighting(node.getFirst(), node.getSecond()));
		}

		// Line breakers
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		// Find all shortest distances from 0
		System.out.println("Shortest paths starting from 0");
		// Gets the shortest paths
		Map<Integer, Deque<Integer>> map = graph.findShortestPaths(0);

		// For each integer
		for (Integer i : map.keySet()) {
			// Tally up the weights of each edge
			int distance = 0;

			ArrayList<Integer> list = new ArrayList<>(map.get(i));

			for (int index = 0; index < list.size() - 1; index++) {
				distance += graph.getWeighting(list.get(index), list.get(index + 1));
			}

			// Print it out
			System.out.printf("%d ---> %d, length: %d%n", 0, i, distance);
		}

		// Test regular path finding with DFS
		System.out.println("Path Test");
		ArrayDeque<Integer> stack = new ArrayDeque<>();
		Set<Integer> visited = new HashSet<>();
		if (graph.findPath(0, 4, stack, visited)) {
			System.out
					.println("Path found: " + stack.stream().map(Number::toString).collect(Collectors.joining(" -> ")));
		}

		System.out.println("MST test");
		Graph<Integer> mst = graph.createMST();

		// Print the weight of each edge

		Set<Twin<Integer>> nodes = new HashSet<>();
		for (Twin<Integer> node : mst.getEdges()) {

			if (nodes.add(node))
				System.out.printf("%d <----> %d, weight: %d%n", node.getFirst(), node.getSecond(),
						graph.getWeighting(node.getFirst(), node.getSecond()));
		}

	}
}