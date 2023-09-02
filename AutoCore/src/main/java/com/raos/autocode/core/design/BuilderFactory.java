package com.raos.autocode.core.design;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.design.builder.Builder;
import com.raos.autocode.core.design.builder.SimpleBuilder;

/**
 * 
 * Used to get a specific builder based on class Used in large CDI applications
 * where dependancy injection would happen with a context. And you would need an
 * object builder on demand
 * 
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
public interface BuilderFactory {

	/**
	 * Retrieves the builder
	 * 
	 * @param <T>   - builder type
	 * @param clazz - class type
	 * @return builder
	 */
	<T> Builder<T> getBuilder(Class<T> clazz);

	/**
	 * Get the default builder from the class
	 * 
	 * @return the default builder
	 */
	public static BuilderFactory getDefault() {
		// Return default builder
		return new BuilderFactory() {

			@Override
			public <T> Builder<T> getBuilder(Class<T> clazz) {
				return new SimpleBuilder<>(clazz);
			}
		};
	}
}
