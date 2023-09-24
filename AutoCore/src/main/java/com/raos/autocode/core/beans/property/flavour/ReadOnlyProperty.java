package com.raos.autocode.core.beans.property.flavour;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.beans.property.Property;

/**
 * Marks property as read only. Which means that only owner bean can write to it
 * 
 * @author aditya
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-15")
public interface ReadOnlyProperty<T> extends Property<T> {

}
