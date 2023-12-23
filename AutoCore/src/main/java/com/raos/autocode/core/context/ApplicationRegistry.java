package com.raos.autocode.core.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.application.ApplicationException;

/**
 * The main application registry for our dependency injection system
 * 
 * @author aditya
 * @date Dec. 20, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 20, 2023")
class ApplicationRegistry implements DIRegistery {
	// Default name
	public static final String DEFAULT_NAME = "##DEFAULT##";
	// Default class
	public static final Class<?> DEFAULT_CLASS = void.class;

	// Create a multikeyed map
	private List<BeanDescriptor> registries;

	/**
	 * Create the application registry
	 */
	public ApplicationRegistry() {
		// Create the maps
		registries = new ArrayList<>();
	}

	@Override
	public Iterator<BeanDescriptor> iterator() {
		// Map the bean registry
		return registries.stream()
				// Convert it into an iterator
				.map(BeanDescriptor::copy).iterator();
	}

	@Override
	public void close() throws Exception {
		// Clear the maps
		registries.clear();
	}

	@Override
	public <T> Optional<T> getBean(Class<T> clazz) {

		// Get the bean and cast
		return getBean(clazz, DEFAULT_NAME);
	}

	@Override
	public <T> Optional<T> getBean(Class<T> clazz, String name) {
		// Get the identity from the name
		return registries
				// Stream the list
				.stream()
				// Filter the name
				.filter(bean -> bean.getName().equals(name) || name.equals(DEFAULT_NAME))
				// Filter the class
				.filter(bean -> bean.getClazz().equals(clazz) || clazz.equals(DEFAULT_CLASS))
				// Map the descriptor to the objects
				.map(BeanDescriptor::getObject)
				// Cast the map
				.map(clazz::cast)
				// Find any
				.findAny();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Optional<T> getBean(String name) {

		// Get the bean and cast
		return (Optional<T>) getBean(DEFAULT_CLASS, name);
	}

	@Override
	public boolean hasBean(String name) {
		// Check if the bean is present
		return getBean(name).isPresent();
	}

	@Override
	public boolean hasBean(String name, Class<?> clazz) {
		// Check if the bean is present
		return getBean(clazz, name).isPresent();
	}

	@Override
	public boolean hasBean(Class<?> clazz) {
		// Check if the bean is present
		return getBean(clazz).isPresent();
	}

	@Override
	public <T> void putBean(String name, T object) {
		// Check
		Objects.requireNonNull(name);
		Objects.requireNonNull(object);

		// Check if the bean is present previously
		if (hasBean(object.getClass()))
			throw new ApplicationException("Unable to add bean. Bean with no name has been already added");

		// Check if the bean with name is present previously
		if (hasBean(name))
			throw new ApplicationException(
					String.format("Unable to add bean. Bean with name \'%s\' has already been added", name));

		// add the values to the registry
		registries.add(new BeanDescriptor(object, object.getClass(), name));

	}

	@Override
	public <T> void putBean(T object) {
		// Check
		Objects.requireNonNull(object);

		// Check if the bean is present previously
		if (hasBean(object.getClass()))
			throw new ApplicationException("Unable to add bean. Bean with no name has been already added");

		// add the values to the registry
		registries.add(new BeanDescriptor(object, object.getClass(), DEFAULT_NAME));

	}

	@Override
	public void removeBean(String name) {
		removeBean(name, DEFAULT_CLASS);

	}

	@Override
	public void removeBean(String name, Class<?> clazz) {
		// Check if the bean with name is present
		if (!hasBean(name, clazz))
			throw new ApplicationException(
					String.format("Unable to remove bean. Bean with name \'%s\' does not exist", name));

		// Remove name
		registries.remove(registries
				// Stream the list
				.stream()
				// Filter the name
				.filter(bean -> bean.getName().equals(name) || name.equals(DEFAULT_NAME))
				// Filter the class
				.filter(bean -> bean.getClazz().equals(clazz) || clazz.equals(DEFAULT_CLASS))
				// Find the values
				.findAny()
				// Get the values
				.get());

	}

	@Override
	public void removeBean(Class<?> clazz) {
		// Remove bean
		removeBean(DEFAULT_NAME, clazz);

	}

}
