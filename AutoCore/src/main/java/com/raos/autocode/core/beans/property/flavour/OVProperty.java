package com.raos.autocode.core.beans.property.flavour;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Observable and Validatable Property (useful for being able to observe and
 * validate changes)
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface OVProperty<T> extends ObservableProperty<T>, ValidatableProperty<T> {

}
