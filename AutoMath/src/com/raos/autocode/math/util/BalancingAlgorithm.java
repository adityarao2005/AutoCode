package com.raos.autocode.math.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import com.raos.autocode.core.util.Pair;
import com.raos.autocode.math.parsing.ExpressionParsingException;

// Container for balancing algorithm
public interface BalancingAlgorithm {

	// Balancing split
	public static String[] balancingSplit(String str, char open, char closed, char split) {
		// return the array
		List<String> values = new ArrayList<>();

		// Balancing algorithm
		int balance = 0;
		int startExpr = 0;

		// loop over all the characters in the loop
		for (int i = 0; i < str.length(); i++) {
			// if the character is an open brace, then increase the balance
			if (str.charAt(i) == open) {
				balance++;
			} else {
				// If the character is a closed brace
				if (str.charAt(i) == closed) {
					// if the balance is greater than 0, then decrease the balance
					if (balance > 0)
						balance--;
					// Otherwise throw an error
					else {
						throw new ExpressionParsingException(String.format("Invalid character found at index: %d", i));
					}

					// If the balance is 0, then grab the start to the end
					if (balance == 0) {
						// Reached the end
						values.add(str.substring(startExpr, i).trim());

						// Set the start expr
						startExpr = str.substring(i).indexOf(split) + 1;

					}

				}
			}
		}

		// Return the array
		return values.toArray(String[]::new);
	}

	// Balancing split
	@SuppressWarnings("deprecation")
	public static Pair<String, List<String>> balancingStore(String str, char open, char closed) {
		// return the array
		List<String> values = new ArrayList<>();

		// Balancing algorithm
		int balance = 0;
		int startExpr = 0;

		// loop over all the characters in the loop
		for (int i = 0; i < str.length(); i++) {
			// if the character is an open brace, then increase the balance
			if (str.charAt(i) == open) {
				// Set the start expression
				if (balance == 0)
					startExpr = i;
				
				// Increase the balance
				balance++;
			} else {
				// If the character is a closed brace
				if (str.charAt(i) == closed) {
					// if the balance is greater than 0, then decrease the balance
					if (balance > 0)
						balance--;
					// Otherwise throw an error
					else {
						throw new ExpressionParsingException(String.format("Invalid character found at index: %d", i));
					}

					// If the balance is 0, then grab the start to the end
					if (balance == 0) {
						// Reached the end
						values.add(str.substring(startExpr, i).trim());

						// Set the start expr
						startExpr = i + 1;

					}

				}
			}
		}

		// Replace the occurrences of the values
		for (int i = 0; i < values.size(); i++)
			str = str.replace(values.get(i), "@" + i);
		
		return new Pair<>(str, values);
	}

	@SuppressWarnings("deprecation")
	public static void balancingStore(String expression) {

		// Arraylist of left brackets
		ArrayList<Integer> leftBrackets = new ArrayList<Integer>();
		ArrayList<Integer> rightBrackets = new ArrayList<Integer>();

		// Add the brackets to the arraylists
		for (int i = 0; i < expression.length(); ++i) {
			if (expression.charAt(i) == '(') {
				leftBrackets.add(i);
			} else if (expression.charAt(i) == ')') {
				rightBrackets.add(i);
			}
		}

		// merge the left brackets and right brackets array
		LinkedList<Pair<String, Integer>> merge = new LinkedList<>();

		// Create a left and right pointers
		int L = 0, R = 0;

		// Add the brackets to the queue
		while (L < leftBrackets.size() && R < rightBrackets.size()) {
			if (leftBrackets.get(L) < rightBrackets.get(R)) {
				merge.add(new Pair<>("L", leftBrackets.get(L)));
				L++;
			} else {
				merge.add(new Pair<>("R", rightBrackets.get(R)));
				R++;
			}
		}

		// Add the remainder of the brackets
		while (L < leftBrackets.size()) {
			merge.add(new Pair<>("L", leftBrackets.get(L)));
			L++;
		}

		while (R < rightBrackets.size()) {
			merge.add(new Pair<>("R", rightBrackets.get(R)));
			R++;
		}

		// we note that the left most right bracket must be paired with the closest left
		// bracket on it's left side
		ListIterator<Pair<String, Integer>> iterator = merge.listIterator(0);

		// Create an indexed result
		ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();

		// While we have more brackets
		while (iterator.hasNext()) {
			// Get the first pair of brackets
			Pair<String, Integer> p = iterator.next();
			// If the type is right bracket
			if (Objects.equals(p.getFirst(), "R")) {
				// Remove the right bracket
				iterator.remove();
				// Add a new indexed pair
				result.add(new Pair<>(iterator.previous().getSecond(), p.getSecond()));
				// Remove the left bracket
				iterator.remove();
			}
		}

		// Print out the result
		ArrayList<String> subStringArray = new ArrayList<>();
		for (Pair<Integer, Integer> p : result) {
			// include all the characters (include brackets)
			subStringArray.add(expression.substring(p.getFirst(), p.getSecond() + 1));
		}
	}

}
