package com.raos.autocode.core.util;

public interface Callback<R, V> {

	R call(V arg);
}
