//package com.raos.autocode.core.deprecated.beans.property.bindings;
//
//import java.util.function.Function;
//
//import com.raos.autocode.core.beans.property.ObservableProperty;
//import com.raos.autocode.core.beans.property.Property;
//
//// Binding Support, to aid in bindings
//@Deprecated(forRemoval = true)
//public abstract class BindingSupport<T, V> {
//
//	// Properties
//	private Property<V> binder;
//	private Property<T> bound;
//	private Function<V, T> toBound;
//	private Function<T, V> toBinder;
//
//	// Constructor
//	public BindingSupport(Property<T> bound, Property<V> binder, Function<V, T> toBound, Function<T, V> toBinder) {
//		super();
//		this.binder = binder;
//		this.bound = bound;
//		this.toBound = toBound;
//		this.toBinder = toBinder;
//	}
//
//	// Getters
//	public ObservableProperty<V> getBinder() {
//		return (ObservableProperty<V>) binder;
//	}
//
//	public ObservableProperty<T> getBound() {
//		return (ObservableProperty<T>) bound;
//	}
//
//	public Function<V, T> getBoundConverterCallback() {
//		return toBound;
//	}
//
//	public Function<T, V> getBinderConverterCallback() {
//		return toBinder;
//	}
//
//	// Uses binding as contract
//	public abstract void bind();
//
//	// Unbinds contract
//	public abstract void unbind();
//}
