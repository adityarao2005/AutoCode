package com.raos.autocode.core.util;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Multi valued Map
 * @author aditya
 *
 * @param <K>
 * @param <V>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public class MultiMap<K, V> extends AbstractMap<K, List<V>> {
	// Map
	private Map<K, List<V>> internalMap;

	// Constructor
	public MultiMap() {
		internalMap = new HashMap<>();
	}

	/**
	 * Put to the map
	 */
	@Override
	public List<V> put(K key, List<V> value) {
		return internalMap.put(key, value);
	}

	/**
	 * Put a pair into the multi value map
	 * @param key
	 * @param value
	 */
	public void put(K key, V value) {

		// Internal map
		if (!internalMap.containsKey(key)) {
			put(key, new ArrayList<>());
		}

		// Add the value to the array
		internalMap.get(key).add(value);
	}

	/**
	 * Set value in multi map
	 * @param key
	 * @param value
	 */
	public void set(K key, V value) {
		// If the map has the key, then clear the list
		if (internalMap.containsKey(key))
			internalMap.get(key).clear();
		// put the value
		put(key, value);
	}

	/**
	 * Removes value
	 */
	@Override
	public boolean remove(Object key, Object value) {
		if (internalMap.containsKey(key))
			return internalMap.get(key).remove(value);
		return false;
	}

	/**
	 * Contains value
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean containsValue(K key, V value) {
		if (internalMap.containsKey(key))
			return internalMap.get(key).contains(value);

		return false;
	}

	/**
	 * Get entry set
	 */
	@Override
	public Set<Entry<K, List<V>>> entrySet() {
		return internalMap.entrySet();
	}

	/**
	 * Get optional
	 * @param key
	 * @return
	 */
	public Optional<V> getOptional(K key) {
		return get(key).stream().findFirst();
	}

	/**
	 * Get
	 */
	@Override
	public List<V> get(Object key) {
		return internalMap.get(key);
	}
}
