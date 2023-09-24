package com.raos.autocode.core.beans.property.flavour;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Read Only, Observable, and validatable (not really useful in development but
 * providing option here)
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface ROOVProperty<T> extends ObservableProperty<T>, ReadOnlyProperty<T>, ValidatableProperty<T> {

}
