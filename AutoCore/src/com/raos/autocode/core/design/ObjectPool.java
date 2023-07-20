package com.raos.autocode.core.design;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

// Represents an object pool, creates a bunch of reusable objects
public abstract class ObjectPool<T extends AutoCloseable> implements AutoCloseable {
	// Logger
	private static final Logger LOGGER = Logger.getLogger(ObjectPool.class.getName());
	// Max and min fields, if all these fields are -1, then itll create fields on
	// demand
	private final int maxIdle; // if -1, no maximum, pool keeps growing (don't do this)
	private final int maxOpen; // if -1, no maximum used values, pool keeps growing (don't do this)
	private final int timeout; // if -1, then no timeout

	// Collections
	private BlockingQueue<T> idleQueue;
	private Collection<PoolableObject> openSet;

	// Use this constructor
	public ObjectPool(int maxIdle, int minIdle, int maxOpen, int timeout) {
		// Set the values
		this.maxIdle = maxIdle <= 0 ? Integer.MAX_VALUE : maxIdle;
		minIdle = minIdle <= 0 ? 1 : minIdle;
		this.maxOpen = maxOpen <= 0 ? Integer.MAX_VALUE : maxOpen;
		this.timeout = timeout;

		// Create the open set
		openSet = Collections.synchronizedCollection(new HashSet<>(maxOpen));
		// Create the queue
		idleQueue = new LinkedBlockingQueue<>(maxIdle);

		// Fill the queue
		for (int i = 0; i < minIdle; i++) {
			idleQueue.add(newInstance());
		}

	}

	// Get an object
	public PoolableObject get() throws Exception {
		// If the maximum connections are being used.. return null and alert user
		if (openSet.size() >= maxOpen) {
			LOGGER.log(Level.SEVERE, "The maximum amount of connections are already being used");
			return null;
		} else {

			// If maximum idle connections reached.. poll with time
			if (maxIdle <= idleQueue.size())
				if (timeout >= 0)
					return new PoolableObject(idleQueue.poll(timeout, TimeUnit.SECONDS));
				else
					return new PoolableObject(idleQueue.take());
			else {
				// Add object to collection
				PoolableObject object = new PoolableObject(newInstance());
				openSet.add(object);
				return object;
			}
		}
	}

	// To create a new Instance
	protected abstract T newInstance();

	// Create the poolable object
	public class PoolableObject implements AutoCloseable {
		// Value
		private T value;

		// Ctor
		public PoolableObject(T value) {
			this.value = value;
		}

		// Get value
		public T getValue() {
			return value;
		}

		// On close, add value back to queue and remove from set
		@Override
		public void close() throws Exception {
			// If the size of the idleQueue is less than the maxIdle, then add
			if (idleQueue.size() < maxIdle)
				idleQueue.add(value);
			else
				// Otherwise, close the resource
				value.close();
			// Set value to null and remove this from set
			value = null;
			openSet.remove(this);
		}

	}

	@Override
	public void close() throws Exception {

		// Remvove all the values form the openSet
		for (PoolableObject obj : openSet)
			obj.close();

		// Close all values from idleQueue
		while (!idleQueue.isEmpty())
			idleQueue.poll().close();
	}

}
