package com.raos.autocode.core.beans.property.bindings;

import java.util.function.Function;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.event.PropertyChangeFilter;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

// Creates a unidirectional binding
public final class UniDirectionalBinding<T, V> extends BindingSupport<T, V> {
	// Used for the bindings
	private final PropertyChangeListener<V> binderChangeListener = (obs, old, newv) -> {
		// When the binder value changes
		getBound().set(getBoundConverterCallback().apply(newv));
	};

	private final PropertyChangeFilter<T> boundFilter = (obs, newv) -> {
		// Make sure that the new value is the binder value
		// we would know if its bound or not
		return newv.equals(getBoundConverterCallback().apply(getBinder().get()));
	};

	// Constructor for uni directional binding
	public UniDirectionalBinding(Property<T> bound, Property<V> binder, Function<V, T> toBound) {
		super(bound, binder, toBound, null);
	}

	@Override
	public void bind() {
		// Add the listeners
		getBinder().getListeners().add(binderChangeListener);
		getBound().getFilters().add(boundFilter);
		// Apply value
		getBound().set(getBoundConverterCallback().apply(getBinder().get()));
		
		// Add to binding contract
		Bindings.BINDING_CONTRACT_MANAGER.put(System.identityHashCode(getBound()), this);
	}

	@Override
	public void unbind() {

		// Remove listeners
		getBinder().getListeners().remove(binderChangeListener);
		getBound().getFilters().remove(boundFilter);

		// remove contract
		Bindings.BINDING_CONTRACT_MANAGER.remove(System.identityHashCode(getBound()));

	}

}
