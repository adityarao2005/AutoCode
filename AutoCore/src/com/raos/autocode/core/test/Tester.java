package com.raos.autocode.core.test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;

import com.raos.autocode.core.ds.Graph;

/*
 * This is the AutoCore tester area
 * This framework will have basic utilities:
 * - Basic algorithms and Data structures
 * - Basic Design patterns and interfaces
 * - Wrapping IO
 * - Basic Reflection
 * - Creating Properties and Pseudo-Objects
 * - Creating Object Templates
 */
public class Tester {

	public static void main(String[] args) {
		testGraphClass();
	}

	public static void testGraphClass() {
		// Creating a graph tester
		Graph<Integer> graph = new Graph<Integer>();

		// Adding the graph paths
		graph.addPath(0, 1, true, 4);
		graph.addPath(0, 7, true, 8);
		graph.addPath(1, 7, true, 11);
		graph.addPath(1, 2, true, 8);
		graph.addPath(7, 8, true, 7);
		graph.addPath(7, 6, true, 1);
		graph.addPath(2, 8, true, 2);
		graph.addPath(2, 3, true, 7);
		graph.addPath(2, 5, true, 4);
		graph.addPath(8, 6, true, 6);
		graph.addPath(6, 5, true, 2);
		graph.addPath(6, 5, true, 2);
		graph.addPath(3, 5, true, 14);
		graph.addPath(3, 4, true, 9);
		graph.addPath(5, 4, true, 10);

		// Print the weight of each edge
		for (Integer node : graph.getAdjacencyList().keySet()) {
			for (Integer endNode : graph.getAdjacencyList().get(node)) {

				System.out.printf("%d <----> %d, weight: %d%n", node, endNode, graph.getWeighting(node, endNode));

			}
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

	}
}
