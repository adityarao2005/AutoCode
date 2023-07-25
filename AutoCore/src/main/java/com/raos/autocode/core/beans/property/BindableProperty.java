package com.raos.autocode.core.beans.property;

public interface BindableProperty<T> extends ObservableProperty<T> {
	
	public boolean isBound();
	
	public void bind(ObservableProperty<T> property);
	
	public void unbind();
}
