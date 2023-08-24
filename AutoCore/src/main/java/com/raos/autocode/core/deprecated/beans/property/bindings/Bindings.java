package com.raos.autocode.core.deprecated.beans.property.bindings;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;

// All bindings supported via ObservableProperty
@Deprecated(forRemoval = true)
public final class Bindings {
	// Map to signify which property has been bound to which
	protected static final Map<Integer, BindingSupport<?, ?>> BINDING_CONTRACT_MANAGER = new ConcurrentHashMap<>();

	private Bindings() {
	}

	// Binds the properties
	public static boolean isBound(Property<?> bound) {
		return BINDING_CONTRACT_MANAGER.containsKey(System.identityHashCode(bound));
	}

	// Gets the binding support
	public static BindingSupport<?, ?> getBindingSupport(Property<?> property) {
		return BINDING_CONTRACT_MANAGER.get(System.identityHashCode(property));
	}

	// Binds the properties
	public static <T> void bind(Property<T> bound, Property<T> binder) {
		bindUnrelated(bound, binder, Function.identity());
	}

	// Binds the properties
	public static <T> void bindBiDirectional(Property<T> bound, Property<T> binder) {
		bindBiDirectionalUnrelated(bound, binder, Function.identity(), Function.identity());
	}

	// Bind unrelationed properties
	public static <T, V> void bindUnrelated(Property<T> bound, Property<V> binder, Function<V, T> converter) {
		// Check whether both of these are already bound values
		if (bound instanceof ObservableProperty && binder instanceof ObservableProperty) {
			// Set bound
			if (isBound(bound))
				throw new BindingException("The property to be bound is already bound. Unbind first");

			BindingSupport<T, V> bindings = new UniDirectionalBinding<>(bound, binder, converter);
			bindings.bind();

		} else {
			// Throw exception
			throw new BindingException(
					"either the bound property is not an observable property or the binder is not an observable property");
		}
	}

	// Bind unrelationed properties
	public static <T, V> void bindBiDirectionalUnrelated(Property<T> bound, Property<V> binder,
			Function<V, T> converterF, Function<T, V> converterB) {
		// Check whether both of these are already bound values
		if (bound instanceof ObservableProperty && binder instanceof ObservableProperty) {
			// Set bound
			if (isBound(bound) || isBound(binder))
				throw new BindingException("The property to be bound is already bound. Unbind first");

			BindingSupport<T, V> bindings = new BiDirectionalBinding<>(bound, binder, converterF, converterB);
			bindings.bind();

		} else {
			// Throw exception
			throw new BindingException(
					"either the bound property is not an observable property or the binder is not an observable property");
		}
	}

	// Unbind property
	public static <T> void unbind(Property<T> bound) {

		if (bound instanceof ObservableProperty) {

			// Remove listener and filter and bound property
			BindingSupport<?, ?> bindings = getBindingSupport(bound);
			bindings.unbind();

		} else {
			throw new BindingException("the property passed is not a bindable property");
		}
	}

}
