//package com.raos.autocode.core.deprecated.beans.property.bindings;
//
//import java.util.function.Function;
//
//import com.raos.autocode.core.beans.property.Property;
//import com.raos.autocode.core.beans.property.event.PropertyChangeListener;
//
//// Creates a unidirectional binding
//@Deprecated(forRemoval = true)
//public final class BiDirectionalBinding<T, V> extends BindingSupport<T, V> {
//	// generic listener
//	private boolean call;
//
//	// Make sure that the generic call listener
//	private final PropertyChangeListener<?> genericListener = this::propertyChangeCallback;
//
//	// Constructor for uni directional binding
//	public BiDirectionalBinding(Property<T> bound, Property<V> binder, Function<V, T> toBound,
//			Function<T, V> toBinder) {
//		super(bound, binder, toBound, toBinder);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public void bind() {
//		getBinder().getListeners().add((PropertyChangeListener<V>) genericListener);
//		getBound().getListeners().add((PropertyChangeListener<T>) genericListener);
//
//		// Add to binding contract
//		Bindings.BINDING_CONTRACT_MANAGER.put(System.identityHashCode(getBound()), this);
//		Bindings.BINDING_CONTRACT_MANAGER.put(System.identityHashCode(getBinder()), this);
//	}
//
//	@Override
//	public void unbind() {
//		getBinder().getListeners().remove(genericListener);
//		getBound().getListeners().remove(genericListener);
//
//		// Remove binding contract
//		Bindings.BINDING_CONTRACT_MANAGER.remove(System.identityHashCode(getBound()));
//		Bindings.BINDING_CONTRACT_MANAGER.remove(System.identityHashCode(getBinder()));
//	}
//
//	@SuppressWarnings("unchecked")
//	private void propertyChangeCallback(Property<?> obs, Object oldv, Object newv) {
//
//		// Set call to false and return
//		if (call) {
//			call = false;
//			return;
//		}
//
//		// if this is the binder, then set bound
//		if (obs.equals(this.getBinder())) {
//
//			getBound().set(getBoundConverterCallback().apply((V) newv));
//			// else use set binder
//		} else {
//
//			getBinder().set(getBinderConverterCallback().apply((T) newv));
//		}
//
//		call = true;
//	}
//}
