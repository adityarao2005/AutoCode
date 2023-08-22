package com.raos.autocode.core.beans;

import java.util.Objects;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;
import com.raos.autocode.core.util.ReflectionUtil;

// Abstract Property
abstract class AbstractProperty<T> implements Property<T> {
	// Fields
	private String name;
	private T value;
	private PropertyManager bean;
	@SuppressWarnings("unchecked")
	private Class<T> type = (Class<T>) Object.class;
	private boolean nullable;
	private boolean readOnly;

	// Constructors
	public AbstractProperty() {

	}

	public AbstractProperty(String name, PropertyManager bean, Class<T> type, boolean nullable, boolean readOnly) {
		this();
		this.name = name;
		this.bean = bean;
		this.type = type;
		this.nullable = nullable;
		this.readOnly = readOnly;
	}

	public AbstractProperty(String name, PropertyManager bean, Class<T> type, boolean nullable, boolean readOnly,
			T value) {
		this(name, bean, type, nullable, readOnly);
		this.value = value;
	}

	// Getters and Setters
	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected T _internal_getValue() {
		return value;
	}

	protected void _internal_setValue(T value) {
		Class<?> caller = ReflectionUtil.getCaller(2);
		if (!caller.isAssignableFrom(this.getClass()))
			throw new IllegalAccessError("No accessing this via reflection or methodhandles");

		this.value = value;
	}

	@Override
	public T get() {
		return _internal_getValue();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void set(Object value) {

		// Say that the property is read only
		if (readOnly) {
			if (!ReflectionUtil.checkCaller(bean.getClass()) && !ReflectionUtil.checkCallerMax(BeanClass.class, 10))
				throw new IllegalAccessError(
						"This property is read only and cannot be mutated by other classes other than the enclosing bean");
		}

		if (!nullable && value == null)
			throw new NullPointerException("Null values are not allowed");

		// Type check
		if (!type.isInstance(value))
			throw new ClassCastException("The argument passed is not a valid type");

		_internal_setValue((T) value);
	}

	@Override
	public PropertyManager getBean() {
		return bean;
	}

	public void setBean(PropertyManager bean) {
		this.bean = bean;
	}

	@Override
	public Class<T> getType() {
		return type;
	}

	public void setType(Class<T> type) {
		this.type = type;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractProperty<?> other = (AbstractProperty<?>) obj;
		return Objects.equals(type, other.type) && Objects.equals(value, other.value);
	}

	@Override
	public String toString() {
		return value.toString();
	}
}
