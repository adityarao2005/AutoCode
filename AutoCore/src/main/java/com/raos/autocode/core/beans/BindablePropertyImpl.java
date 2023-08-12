package com.raos.autocode.core.beans;

import com.raos.autocode.core.beans.property.BindableProperty;
import com.raos.autocode.core.beans.property.ObservableProperty;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.beans.property.bindings.BindingException;
import com.raos.autocode.core.beans.property.event.PropertyChangeListener;

// Unidirectional Binding
// Bidirectional binding is slightly tricky
public class BindablePropertyImpl<T> extends ObservablePropertyImpl<T> implements BindableProperty<T> {
	// Binder
	private ObservableProperty<T> binder;
	// Binding listener
	private PropertyChangeListener<T> bindListener = (prop, oldv, newv) -> {
		BindablePropertyImpl.this.set(newv);
	};

	// Construtors
	public BindablePropertyImpl() {
		super();
	}

	public BindablePropertyImpl(String name, PropertyManager bean, Class<T> type, boolean readOnly, boolean nullable) {
		super(name, bean, type, nullable, readOnly);
	}

	public BindablePropertyImpl(String name, PropertyManager bean, Class<T> type, boolean readOnly, boolean nullable,
			T value) {
		super(name, bean, type, nullable, readOnly, value);
	}

	@Override
	public void set(Object value) {
		// Check if setting the value the natural way is ok
		// This is only acceptable if isBound is false OR the bound value is the same as
		// the current value
		if (!isBound() || (isBound() && value.equals(binder.get())))
			super.set(value);
//		else
//			TODO: handle value here
	}

	// Gets binder
	public ObservableProperty<T> getBinder() {
		return binder;
	}

	public void setBinder(ObservableProperty<T> binder) {
		this.binder = binder;
	}

	// Binds
	public void bind(ObservableProperty<T> property) {
		if (isBound())
			throw new BindingException("The property is already bound. Unbind first");

		set(property.get());
		property.getListeners().add(bindListener);
		setBinder(property);
	}

	// Unbinds
	public void unbind() {

		// Remove the listener and remove binding
		getBinder().getListeners().remove(bindListener);
		setBinder(null);
	}

	// Check if bound
	public boolean isBound() {
		return binder == null;
	}

}
