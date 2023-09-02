package com.raos.autocode.core.beans;

import java.beans.PropertyDescriptor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.util.ExceptionUtil;

/**
 * Wraps a property and acts as a handle to the property
 * 
 * @author Raos
 *
 * @param <T> - The type of the property wrapped
 */
@ClassPreamble(author = "Aditya Rao", date = "7/5/2023")
public class JavaBeanPropertyWrapper<T> implements Property<T> {
	// Allows for accessing the property
	private JavaBeanPropertyAccessor<T> accessor;
	// Reference to the manager if present
	private JavaBeanPropertyManager manager;

	/**
	 * Creates a property wrapper
	 * 
	 * @param accessor - Allows for accessing and mutating java bean property
	 * @param manager  - a java bean property manager (optional)
	 */
	public JavaBeanPropertyWrapper(JavaBeanPropertyAccessor<T> accessor, JavaBeanPropertyManager manager) {
		this.accessor = accessor;
		this.manager = manager;
	}

	/**
	 * Gets the value of the property
	 */
	@Override
	public T get() {
		return accessor.getValue();
	}

	/**
	 * Sets the value of the property
	 */
	@Override
	public void set(Object value) {
		accessor.setValue(value);
	}

	/**
	 * Gets the name of the property
	 */
	@Override
	public String getName() {
		return accessor.getName();
	}

	/**
	 * Gets the owner/bean of the property
	 */
	@Override
	public PropertyManager getBean() {
		return manager;
	}

	/**
	 * Gets the type of the property
	 */
	@Override
	public Class<T> getType() {
		return accessor.getType();
	}

	/**
	 * Allows for accessing and mutating of a property
	 */
	// Accesses and Mutates the bean via the bean wrapper
	public static interface JavaBeanPropertyAccessor<T> {

		/**
		 * Gets the value of the property
		 */
		T getValue();

		/**
		 * Sets the value of the property
		 */
		void setValue(Object value);

		/**
		 * Gets the name of the property
		 */
		String getName();

		/**
		 * Gets the type of the property
		 */
		Class<T> getType();
	}

	/**
	 * Gets access to property via VarHandle API
	 */
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

	/**
	 * Gets access to property via MethodHandle API
	 */
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

	/**
	 * Gets access to property via PropertyDescriptor API
	 */
	public static class PropertyDescriptorAccessor<T> implements JavaBeanPropertyAccessor<T> {
		private Method getterHandle;
		private Method setterHandle;
		private Object bean;
		private String name;

		public PropertyDescriptorAccessor(Object bean, String name) {
			this(bean, ExceptionUtil.throwSilently(() -> new PropertyDescriptor(name, bean.getClass())).get());
		}

		public PropertyDescriptorAccessor(Object bean, PropertyDescriptor descriptor) {

			getterHandle = descriptor.getReadMethod();
			setterHandle = descriptor.getWriteMethod();
			this.bean = bean;
			this.name = descriptor.getName();
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

	/**
	 * Gets access to property using a Supplier and a Consumer to act as getter and
	 * setter
	 */
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

		@SuppressWarnings("unchecked")
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

	/**
	 * Gets access to property via Reflection
	 */
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
