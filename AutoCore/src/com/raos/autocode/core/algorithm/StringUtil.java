package com.raos.autocode.core.algorithm;

import java.util.LinkedList;
import java.util.List;

import com.raos.autocode.core.annotations.ToDo;

@ToDo(description = "Create, understand, and add the following functions to the String Util class", methods = { "prefixFunction", "kmpAlgorithm", "zAlgorithm" })
// Provides some utilities for the string data type
public interface StringUtil {
	// Limit for hashing
	public static long LIMIT = (long) 1e9 + 9;

	// Hashes a string with given number as hasher
	public static long hash(String str, int h) {
		// Passes to overloaded method
		return hash(str.toCharArray(), h);
	}

	// Hashes a character array with a given number as hasher
	public static long hash(char[] str, int p) {
		// Creates the hash variable
		long value = 0;
		long pow = 1;

		// For each character, hash the character
		for (int j = 0; j < str.length; j++) {
			// Hash each character
			value = (value + str[j] * pow) % LIMIT;
			pow = (pow * p) % LIMIT;
		}

		// Return hash
		return value;
	}

	// Hashes a byte array with a given number as hasher
	public static long hash(byte[] str, int p) {
		// Creates the hash variable
		long value = 0;
		long pow = 1;

		// For each character, hash the character
		for (int j = 0; j < str.length; j++) {
			// Hash each character
			value = (value + str[j] * pow) % LIMIT;
			pow = (pow * p) % LIMIT;
		}

		// Return hash
		return value;
	}

	// Searches for a string using the Rabin-Karp Searching algorithm
	// Searches in O(P + T) time where P is length of pattern and T is length of
	// text
	public static List<Integer> rabinKarpSearch(String pattern, String text, int hash) {
		// If the length of the pattern is bigger than the text then there are no
		// occurences
		if (pattern.length() > text.length())
			return List.of();

		// Creates a list
		List<Integer> list = new LinkedList<>();

		// Gets the hashcode of the string
		long pHash = hash(pattern, hash);

		// Substring and get hashcode
		long tHash = hash(text.substring(0, pattern.length()), hash);

		// Check if hashes are the same
		if (pHash == tHash)
			list.add(0);

		int pow = (int) Math.pow(hash, pattern.length() - 1);

		// For every character after the substring
		// Check for hashcode equality
		for (int i = pattern.length(); i < text.length(); i++) {
			// Ditch the first character
			tHash -= text.charAt(i - pattern.length());
			// Divide everything by 31
			tHash /= hash;
			// Add the last character
			tHash = (tHash + text.charAt(i) * pow) % LIMIT;

			// CHeck if hashes are the same
			if (tHash == pHash)
				list.add(i - pattern.length() - 1);
		}

		// Returns the list of occurrences
		return list;
	}


}
