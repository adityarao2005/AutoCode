package com.raos.autocode.core.design;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents an object pool, creates a bunch of reusable objects
 * 
 * @author Raos
 *
 * @param <T> any closeable or expensive resource
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
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

	/**
	 * Gets the poolable object
	 * 
	 * @return
	 * @throws Exception
	 */
	public PoolableObject get() throws Exception {
		// If the maximum connections are being used.. return null and alert user
		if (openSet.size() >= maxOpen) {
			LOGGER.log(Level.SEVERE, "The maximum amount of connections are already being used");
			return null;
		} else {

			// If maximum idle connections reached.. poll with time
			if (maxIdle <= idleQueue.size())
				if (timeout >= 0)
					return create(idleQueue.poll(timeout, TimeUnit.SECONDS));
				else
					return create(idleQueue.take());
			else {
				// Add object to collection
				PoolableObject object = create(newInstance());
				openSet.add(object);
				return object;
			}
		}
	}

	/**
	 * @return an instance
	 */
	protected abstract T newInstance();

	/**
	 * Create a new poolable object. Default implementation. meant to be overriden
	 * for more complicated structures
	 * 
	 * @param instance - new instance
	 * @return a new poolable object
	 */
	protected PoolableObject create(T instance) {
		return new PoolableObject(instance);
	}

	/**
	 * The poolable object
	 * 
	 * @author Raos
	 *
	 */
	public class PoolableObject implements AutoCloseable {
		// Value
		private T value;

		/**
		 * Accepts a value
		 * 
		 * @param value
		 */
		public PoolableObject(T value) {
			this.value = value;
		}

		/**
		 * @return the accepted value
		 */
		public T getValue() {
			return value;
		}

		/**
		 * On close, add value back to queue and remove from set
		 */
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

	/**
	 * Interupts all existing resources and closes them
	 */
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
