package com.raos.autocode.core.beans.property.impl;

import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.util.ExceptionUtil;

// Wraps a java bean property
public class JavaBeanPropertyWrapper<T> implements Property<T> {
	private JavaBeanPropertyAccessor<T> accessor;

	public JavaBeanPropertyWrapper(JavaBeanPropertyAccessor<T> accessor) {
		this.accessor = accessor;
	}

	@Override
	public T get() {
		return accessor.getValue();
	}

	@Override
	public void set(Object value) {
		accessor.setValue(value);
	}

	@Override
	public String getName() {
		return accessor.getName();
	}

	// No bean
	@Override
	public PropertyManager getBean() {
		return null;
	}

	@Override
	public Class<T> getType() {
		return accessor.getType();
	}

	// Accesses and Mutates the bean via the bean wrapper
	public static interface JavaBeanPropertyAccessor<T> {

		T getValue();

		void setValue(Object t);

		String getName();

		Class<T> getType();
	}

	// Var handle variable access
	public static class VarHandleVariableAccessor<T> implements JavaBeanPropertyAccessor<T> {
		private VarHandle varHandle;
		private Object bean;
		private String name;
		private Class<T> clazz;

		public VarHandleVariableAccessor(Object bean, String name, Class<T> clazz) {
			varHandle = ExceptionUtil
					.throwSilently(() -> MethodHandles.privateLookupIn(bean.getClass(), MethodHandles.lookup())
							.findVarHandle(bean.getClass(), name, clazz))
					.get();
			this.bean = bean;
			this.name = name;
			this.clazz = clazz;
		}

		@Override
		public T getValue() {
			return (T) varHandle.get(bean);
		}

		@Override
		public void setValue(Object t) {
			varHandle.set(bean, t);
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Class<T> getType() {
			return clazz;
		}

	}

	// Var handle variable access
	public static class MethodHandleAccessor<T> implements JavaBeanPropertyAccessor<T> {
		private MethodHandle getterHandle;
		private MethodHandle setterHandle;
		private Object bean;
		private String name;
		private Class<T> clazz;

		public MethodHandleAccessor(Object bean, String name, Class<T> clazz) {

			Lookup lookup = ExceptionUtil
					.throwSilently(() -> MethodHandles.privateLookupIn(bean.getClass(), MethodHandles.lookup())).get();

			getterHandle = ExceptionUtil.throwSilently(() -> lookup.findGetter(bean.getClass(), name, clazz)).get();
			setterHandle = ExceptionUtil.throwSilently(() -> lookup.findSetter(bean.getClass(), name, clazz)).get();
			this.bean = bean;
			this.name = name;
			this.clazz = clazz;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T getValue() {
			return (T) ExceptionUtil.throwSilently(() -> getterHandle.invoke(bean)).get();
		}

		@Override
		public void setValue(Object t) {
			ExceptionUtil.throwSilently(() -> setterHandle.invoke(bean, t));
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Class<T> getType() {
			return clazz;
		}

	}

	// Var handle variable access
	public static class PropertyDescriptorAccessor<T> implements JavaBeanPropertyAccessor<T> {
		private Method getterHandle;
		private Method setterHandle;
		private Object bean;
		private String name;

		public PropertyDescriptorAccessor(Object bean, String name) {

			getterHandle = ExceptionUtil
					.throwSilently(() -> new PropertyDescriptor(name, bean.getClass()).getReadMethod()).get();
			setterHandle = ExceptionUtil
					.throwSilently(() -> new PropertyDescriptor(name, bean.getClass()).getWriteMethod()).get();
			this.bean = bean;
			this.name = name;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T getValue() {
			return (T) ExceptionUtil.throwSilently(() -> getterHandle.invoke(bean)).get();
		}

		@Override
		public void setValue(Object t) {
			ExceptionUtil.throwSilently(() -> setterHandle.invoke(bean, t));
		}

		@Override
		public String getName() {
			return name;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Class<T> getType() {
			return (Class<T>) getterHandle.getReturnType();
		}

	}

	// Var handle variable access
	public static class FunctionalAccessor<T> implements JavaBeanPropertyAccessor<T> {
		private Supplier<T> getterHandle;
		private Consumer<T> setterHandle;
		private String name;

		public FunctionalAccessor(String name, Supplier<T> getterHandle, Consumer<T> setterHandle) {

			this.getterHandle = getterHandle;
			this.setterHandle = setterHandle;
			this.name = name;
		}

		@Override
		public T getValue() {
			return getterHandle.get();
		}

		@Override
		public void setValue(Object t) {
			setterHandle.accept((T) t);
		}

		@Override
		public String getName() {
			return name;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Class<T> getType() {
			return (Class<T>) getterHandle.get().getClass();
		}

	}

	// Var handle variable access
	public static class ReflectionVariableAccessor<T> implements JavaBeanPropertyAccessor<T> {
		private Field varHandle;
		private Object bean;
		private String name;

		public ReflectionVariableAccessor(Object bean, String name) {
			varHandle = ExceptionUtil.throwSilently(() -> bean.getClass().getDeclaredField(name)).get();
			varHandle.setAccessible(true);
			this.bean = bean;
			this.name = name;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T getValue() {
			return (T) ExceptionUtil.throwSilently(() -> varHandle.get(bean));
		}

		@Override
		public void setValue(Object t) {
			ExceptionUtil.throwSilently(() -> varHandle.set(bean, t));
		}

		@Override
		public String getName() {
			return name;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Class<T> getType() {
			return (Class<T>) varHandle.getType();
		}

	}
}
