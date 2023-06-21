package com.raos.autocode.math;

// Root for all things math and expression related
public interface Expressable {
	
	// They should be able to return a result
	<T> T result(NumberFormat<T> format);

}
