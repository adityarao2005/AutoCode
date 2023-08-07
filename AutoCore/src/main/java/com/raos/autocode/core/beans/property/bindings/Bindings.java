package com.raos.autocode.core.beans.property.bindings;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.raos.autocode.core.beans.property.BindableProperty;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
import com.raos.autocode.core.beans.property.impl.AbstractProperty;
import com.raos.autocode.core.beans.property.impl.ObservablePropertyImpl;

// Warning, bidirectional bindings not supported yet
public final class Bindings {
	private static final Map<Integer, ObservableProperty<?>> BOUND_PROPERTIES = new HashMap<>();
	private static final Map<Integer, PropertyChangeListener<?>> BOUND_LAMBDAS = new HashMap<>();

	private Bindings() {
	}

	// Binds the properties
	public static <T> void bind(Property<T> bound, Property<T> binder) {
		bindUnrelated(bound, binder, Function.identity());
	}

	// Bind unrelationed properties
	public static <T, V> void bindUnrelated(Property<T> bound, Property<V> binder, Function<V, T> converter) {
		if (bound instanceof BindableProperty && binder instanceof ObservableProperty) {
			// Add proxy
			ObservableProperty<T> proxy = new ObservablePropertyImpl<>();

			// Add the listener
			PropertyChangeListener<V> listener = (prop, oldv, newv) -> {
				proxy.set(converter.apply(newv));
			};

			// Add binding listener
			((ObservableProperty<V>) binder).getListeners().add(listener);

			// Add the bindings to the maps
			BOUND_LAMBDAS.put(((AbstractProperty<?>) bound).deepHashCode(), listener);
			BOUND_PROPERTIES.put(((AbstractProperty<?>) bound).deepHashCode(), ((ObservableProperty<V>) binder));

			// Bind to the proxy
			((BindableProperty<T>) bound).bind(proxy);
		} else
			throw new IllegalArgumentException(
					"either the bound property is not a bindable property or the binder is not an observable property");
	}
	
	// Unbind property
	public static <T> void unbind(Property<T> bound) {
		if (bound instanceof BindableProperty) {
			((BindableProperty<T>) bound).unbind();

			int deepHash = ((AbstractProperty<?>) bound).deepHashCode();

			ObservableProperty<?> prop = BOUND_PROPERTIES.remove(deepHash);
			prop.getListeners().remove(BOUND_LAMBDAS.remove(deepHash));

		} else
			throw new IllegalArgumentException("the property passed is not a bindable property");
	}
}
