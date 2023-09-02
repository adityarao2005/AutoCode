package com.raos.autocode.core.beans.property;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Represents a accessible and mutable reference
 * 
 * @author Raos
 *
 * @param <T> - Object type which is pointed to
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
public interface Pointer<T> extends ReadableProperty<T>, WritableProperty<T> {

	/**
	 * A NULL_POINTER
	 */
	// Returns a null pointer
	public static final Pointer<?> NULL_POINTER = new Pointer<Object>() {

		// Gets null pointer
		@Override
		public Object get() {
			return null;
		}

		// sets null pointer
		@Override
		public void set(Object value) {
			throw new NullPointerException("Cannot set null pointer");
		}
	};

	/**
	 * The equivalence of a property is that the value that the property has is the
	 * same as the value of the other property
	 * 
	 * @param <T>   - the object
	 * @param thiz  - comparing
	 * @param other - compare to
	 * @return equal
	 */
	public static <T> boolean equals(Pointer<T> thiz, Pointer<T> other) {
		// Checks that the value of this property is the same as the value of the other
		// property
		return thiz.get().equals(other.get());
	}

	/**
	 * Checks whether the value of this pointer is null
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	default boolean isNull() {
		return Pointer.equals(this, (Pointer<T>) NULL_POINTER);
	}

	/**
	 * Swap pointer indexes
	 * 
	 * @param <T>
	 * @param pointer1
	 * @param pointer2
	 */
	public static <T> void swap(Pointer<T> pointer1, Pointer<T> pointer2) {
		T value = pointer1.get();
		pointer1.set(pointer2.get());
		pointer2.set(value);
	}
}
