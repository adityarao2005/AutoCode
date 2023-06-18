package com.raos.autocode.core.design;

// Used to get a specific builder based on class
// Used in large CDI applications where dependancy injection would happen with a context
// And you would need an object builder on demand
public interface BuilderFactory {

	<T> Builder<T> getBuilder(Class<T> clazz);
}
