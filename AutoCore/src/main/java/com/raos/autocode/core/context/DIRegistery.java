package com.raos.autocode.core.context;

import java.util.Objects;
import java.util.Optional;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * The dependency registry
 * 
 * @author aditya
 * @date Dec. 17, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface DIRegistery extends Iterable<DIRegistery.BeanDescriptor>, AutoCloseable {

	/**
	 * Gets a bean registered with a certain class. There should only be one object
	 * which is this class to use this method
	 * 
	 * @param <T>   - Class type
	 * @param clazz - class parameter
	 * @return only object of this class
	 */
	<T> Optional<T> getBean(Class<T> clazz);

	/**
	 * Gets a bean registered with a certain class and name.
	 * 
	 * @param <T>   - Class type
	 * @param clazz - class parameter
	 * @param name  - name of bean
	 * @return only object of this class
	 */
	<T> Optional<T> getBean(Class<T> clazz, String name);

	/**
	 * Gets a bean registered with a name.
	 * 
	 * @param <T>  - Class type
	 * @param name - name of bean
	 * @return only object of this class
	 */
	<T> Optional<T> getBean(String name);

	/**
	 * Puts a bean into the registered with a name.
	 * 
	 * @param name   - name of bean
	 * @param object - object to be kept in the registry
	 */
	<T> void putBean(String name, T object);

	/**
	 * Puts a bean into the registry.
	 * 
	 * @param object - object to be kept in the registry
	 */
	<T> void putBean(T object);

	/**
	 * Remove the bean which has this name
	 * 
	 * @param name
	 */
	void removeBean(String name);

	/**
	 * Remove the bean which has this name and class
	 * 
	 * @param name
	 * @param clazz
	 */
	void removeBean(String name, Class<?> clazz);

	/**
	 * Remove the bean(s) which have this class
	 * 
	 * @param clazz
	 */
	void removeBean(Class<?> clazz);

	/**
	 * Checks if beans with this name exist
	 * 
	 * @param name
	 * @return
	 */
	boolean hasBean(String name);

	/**
	 * Check if beans with this name and class exist
	 * 
	 * @param name
	 * @param clazz
	 * @return
	 */
	boolean hasBean(String name, Class<?> clazz);

	/**
	 * Check if beans with this class exist
	 * 
	 * @param clazz
	 * @return
	 */
	boolean hasBean(Class<?> clazz);

	/**
	 * Represents a description of a bean in this registry
	 * 
	 * @author aditya
	 * @date Dec. 17, 2023
	 *
	 */
	@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
	public class BeanDescriptor {
		// Fields
		private Object object;
		private Class<?> clazz;
		private String name;

		// Constructors
		public BeanDescriptor() {
			super();
		}

		public BeanDescriptor(Object object, Class<?> clazz, String name) {
			this.object = object;
			this.clazz = clazz;
			this.name = name;
		}

		// Getters and Setters
		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}

		public Class<?> getClazz() {
			return clazz;
		}

		public void setClazz(Class<?> clazz) {
			this.clazz = clazz;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			return Objects.hash(clazz, name, object);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BeanDescriptor other = (BeanDescriptor) obj;
			return Objects.equals(clazz, other.clazz) && Objects.equals(name, other.name)
					&& Objects.equals(object, other.object);
		}
		
		/**
		 * Copy the descriptor
		 * @param other
		 * @return
		 */
		public static BeanDescriptor copy(BeanDescriptor other) {
			return new BeanDescriptor(other.getObject(), other.getClazz(), other.getName());
		}

	}
}
