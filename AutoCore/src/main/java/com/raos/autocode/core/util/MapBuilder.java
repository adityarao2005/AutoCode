package com.raos.autocode.core.util;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.design.builder.Builder;

/**
 * Builds a map for you
 * 
 * @author aditya
 *
 * @param <K>
 * @param <V>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public class MapBuilder<K, V> implements Builder<Map<K, V>> {
	private Supplier<Map<K, V>> mapSupplier = HashMap::new;
	private List<Entry<K, V>> mapEntries = new LinkedList<>();
	private boolean immutable;

	/**
	 * Creates the map builder
	 * 
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> MapBuilder<K, V> create() {
		return new MapBuilder<>();
	}

	/**
	 * Create the builder from an existing map
	 * 
	 * @param <K>
	 * @param <V>
	 * @param values
	 * @return
	 */
	public static <K, V> MapBuilder<K, V> create(Map<K, V> values) {
		MapBuilder<K, V> map = create();
		map.mapEntries.addAll(values.entrySet());
		return map;
	}

	/**
	 * Add map entry
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public MapBuilder<K, V> addEntry(K key, V value) {
		mapEntries.add(new SimpleImmutableEntry<>(key, value));
		return this;
	}

	/**
	 * Set the class to be initialized
	 * 
	 * @param mapSupplier
	 * @return
	 */
	public MapBuilder<K, V> setMapSupplier(Supplier<Map<K, V>> mapSupplier) {
		this.mapSupplier = mapSupplier;
		return this;
	}

	/**
	 * Set immutable
	 * 
	 * @param immutable
	 * @return
	 */
	public MapBuilder<K, V> setImmutable(boolean immutable) {
		this.immutable = immutable;
		return this;
	}

	@Override
	public Map<K, V> build() {

		// Create the map from class
		Map<K, V> map = mapSupplier.get();

		// Put the entries into the map
		for (Entry<K, V> entry : mapEntries)
			map.put(entry.getKey(), entry.getValue());

		// Get map
		return immutable ? Collections.unmodifiableMap(map) : map;
	}

}
