package com.raos.autocode.core.util.reflection;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * This class is a helper class to access certain attributes of a class
 * 
 * @author Raos
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "8/25/2023")
public class ClassSpy {
	// The spied on class
	private Class<?> spied;
	
	// Creates a new class spier
	ClassSpy(Class<?> spied) {
		this.spied = spied;
	}

}
