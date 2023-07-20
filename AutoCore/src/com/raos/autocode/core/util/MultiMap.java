package com.raos.autocode.core.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

// Multi valued Map
public class MultiMap<K, V> extends AbstractMap<K, List<V>> {
	// Map
	private Map<K, List<V>> internalMap;

	// Constructor
	public MultiMap() {
		internalMap = new HashMap<>();
	}

	// Put to the map
	@Override
	public List<V> put(K key, List<V> value) {
		return internalMap.put(key, value);
	}

	// Put a pair into the multi value map
	public void put(K key, V value) {

		// Internal map
		if (!internalMap.containsKey(key)) {
			put(key, new ArrayList<>());
		}

		// Add the value to the array
		internalMap.get(key).add(value);
	}

	//
	public void set(K key, V value) {
		// If the map has the key, then clear the list
		if (internalMap.containsKey(key))
			internalMap.get(key).clear();
		// put the value
		put(key, value);
	}

	// Removes value
	@Override
	public boolean remove(Object key, Object value) {
		if (internalMap.containsKey(key))
			return internalMap.get(key).remove(value);
		return false;
	}

	// Contains value
	public boolean containsValue(K key, V value) {
		if (internalMap.containsKey(key))
			return internalMap.get(key).contains(value);

		return false;
	}

	// Get entry set
	@Override
	public Set<Entry<K, List<V>>> entrySet() {
		return internalMap.entrySet();
	}

}
