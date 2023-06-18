package com.raos.autocode.core.property;

public interface Pointer<T> extends ReadableProperty<T>, WritableProperty<T> {

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

	// The equivalence of a property is that the value that the property has
	// is the same as the value of the other property
	default boolean equals(Pointer<T> other) {
		// Checks that the value of this property is the same as the value of the other
		// property
		return this.get().equals(other.get());
	}

	// Checks whether the value of this pointer is null
	default boolean isNull() {
		return this.equals(NULL_POINTER);
	}

	// Swap pointer indexes
	static <T> void swap(Pointer<T> pointer1, Pointer<T> pointer2) {
		T value = pointer1.get();
		pointer1.set(pointer2.get());
		pointer2.set(value);
	}
}
