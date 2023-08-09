package com.raos.autocode.core.beans.property.event;

import java.util.logging.Logger;

import com.raos.autocode.core.beans.property.Property;

public class ErrorHandlingPropertyChangeFilter<T> implements PropertyChangeFilter<T> {
	private static Logger logger = Logger.getLogger(ErrorHandlingPropertyChangeFilter.class.getName());

	private PropertyChangeFilter<T> filter;
	private String message;

	public ErrorHandlingPropertyChangeFilter(PropertyChangeFilter<T> filter, String message) {
		this.filter = filter;
		this.message = message;
	}

	@Override
	public boolean filter(Property<T> property, T newv) {
		System.out.println(newv);
		
		boolean value = filter.filter(property, newv);

		if (!value)
			if (message != null || !message.trim().isEmpty())
				logger.warning(message);

		return value;
	}

}
