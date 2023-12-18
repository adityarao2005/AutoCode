package com.raos.autocode.core.beans;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Provides a certain service
 * @author aditya
 * @date Dec. 17, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface IServiceProvider<T extends IService> {

	/**
	 * Returns a service
	 * @return
	 */
	T getService();
}
