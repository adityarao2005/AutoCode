package com.raos.autocode.net;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * 
 * @author aditya
 * @date Oct. 9, 2023
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "Oct. 9, 2023")
public interface ContentInput<T> {

	/**
	 * 
	 * @return
	 */
	public T doRead();
	
	/**
	 * 
	 */
	public boolean hasNext();
}
