package com.raos.autocode.core.beans.property.bindings;

import java.util.function.Function;

import com.raos.autocode.core.beans.property.BindableProperty;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.impl.ObservablePropertyImpl;

public final class Bindings {

	private Bindings() {
	}

	// Binds the properties
	public static <T> void bind(Property<T> bound, Property<T> binder) {
		if (bound instanceof BindableProperty && binder instanceof ObservableProperty)
			((BindableProperty<T>) bound).bind((ObservableProperty<T>) binder);
		else
			throw new IllegalArgumentException(
					"either the bound property is not a bindable property or the binder is not an observable property");
	}

	// Bind unrelationed properties
	public static <T, V> void bindUnrelated(Property<T> bound, Property<V> binder, Function<V, T> converter) {
		if (bound instanceof BindableProperty && binder instanceof ObservableProperty) {
			// TODO: Add name and features
			ObservableProperty<T> proxy = new ObservablePropertyImpl<>();

			// Add binding listener
			((ObservableProperty<V>) binder).getListeners().add((prop, oldv, newv) -> {
				proxy.set(converter.apply(newv));
			});

			// Bind to the proxy
			((BindableProperty<T>) bound).bind(proxy);
		} else
			throw new IllegalArgumentException(
					"either the bound property is not a bindable property or the binder is not an observable property");
	}

	// Unbind property
	public static <T> void unbind(Property<T> bound) {
		if (bound instanceof BindableProperty)
			((BindableProperty<T>) bound).unbind();
		else
			throw new IllegalArgumentException("the property passed is not a bindable property");
	}
}
