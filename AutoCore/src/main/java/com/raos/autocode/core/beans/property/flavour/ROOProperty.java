package com.raos.autocode.core.beans.property.flavour;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Read Only and Observable property (useful for immutable values which you want
 * to detect changes on)
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface ROOProperty<T> extends ReadOnlyProperty<T>, ObservableProperty<T> {

}
