package com.raos.autocode.core.design;

import com.raos.autocode.core.annotations.ClassPreamble;

@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface Factory<T> {

	public T create();
}
