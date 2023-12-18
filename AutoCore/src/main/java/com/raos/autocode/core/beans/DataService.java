package com.raos.autocode.core.beans;

import com.raos.autocode.core.annotations.ClassPreamble;
import com.raos.autocode.core.design.Factory;

/**
 * A class which provides accessibility and mutation to data.
 * 
 * @author aditya
 * @date Dec. 17, 2023
 *
 * @param <T>
 */
@ClassPreamble(author = "Aditya Rao", date = "Dec. 17, 2023")
public interface DataService<T extends Data> extends IService, Factory<T> {

	/**
	 * ALlows for the creation for data
	 */
	@Override
	T create();
}
