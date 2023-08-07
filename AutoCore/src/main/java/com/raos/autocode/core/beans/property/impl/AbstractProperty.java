package com.raos.autocode.core.beans.property.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import com.raos.autocode.core.beans.property.Property;
import com.raos.autocode.core.beans.property.PropertyManager;

// Abstract Property
public abstract class AbstractProperty<T> implements Property<T>, Externalizable {
	// Fields
	private String name;
	private T value;
	private PropertyManager bean;
	private Class<T> type;
	private boolean nullable;

	// Constructors
	public AbstractProperty() {

	}

	public AbstractProperty(String name, PropertyManager bean, Class<T> type, boolean nullable) {
		this();
		this.name = name;
		this.bean = bean;
		this.type = type;
		this.nullable = nullable;
	}

	public AbstractProperty(String name, PropertyManager bean, Class<T> type, boolean nullable, T value) {
		this(name, bean, type, nullable);
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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public T get() {
		return getValue();
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public void set(Object value) {
		if (!nullable && value == null)
			throw new NullPointerException("Null values not allowed");

		// Type check
		if (!type.isInstance(value))
			throw new ClassCastException("The argument passed is not a valid type");

		setValue((T) value);
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

	// Externalizable stuff
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(getName());
		out.writeObject(getType());
		out.writeBoolean(isNullable());
		out.writeObject(getBean());
		out.writeObject(get());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		setName(in.readUTF());
		setType((Class<T>) in.readObject());
		setNullable(in.readBoolean());
		setBean((PropertyManager) in.readObject());
		set((T) in.readObject());
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

}
