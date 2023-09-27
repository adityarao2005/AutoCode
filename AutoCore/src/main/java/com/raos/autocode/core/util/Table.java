package com.raos.autocode.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents a table or resultset
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public class Table implements Iterable<Map<String, Object>> {

	// Table types
	private Map<String, Class<?>> types;
	// Table values
	private List<Map<String, Object>> values;

	// Create a new table instance
	public Table(Map<String, Class<?>> types) {
		this.types = types;
		this.values = new ArrayList<>();
	}

	// Adds record, make sure that the record is matching the map
	public Exception addRecord(Map<String, Object> map) {
		// Create the map
		if (map.entrySet().size() != types.size())
			return new IllegalArgumentException("The map parameters are invalid");

		// Check type of each column
		for (String column : types.keySet()) {
			if (!map.containsKey(column))
				return new IllegalArgumentException("The map parameters are invalid");
		}

		// Add values to record
		values.add(map);

		// Return null for no exception thrown
		return null;
	}

	// Removes record at row
	public void removeRecord(int row) {
		values.remove(row);
	}

	// Gets the record at row
	public Map<String, Object> getRecord(int row) {
		return values.get(row);
	}

	// Get the types
	public Map<String, Class<?>> getTypes() {
		return types;
	}

	// Get the immutable values
	public List<Map<String, Object>> getValues() {
		return Collections.unmodifiableList(values);
	}

	// Make an unmodifiable list iterator
	@Override
	public Iterator<Map<String, Object>> iterator() {
		return getValues().iterator();
	}

	// Create a stream from the map
	public Stream<Map<String, Object>> stream() {
		return values.stream();
	}

	// Equals supplier
	public static Predicate<Map<String, Object>> equals(String property, Object value) {
		return (map) -> map.get(property).equals(value);
	}

}
