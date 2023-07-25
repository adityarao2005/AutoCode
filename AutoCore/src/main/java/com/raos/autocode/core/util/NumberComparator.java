package com.raos.autocode.core.util;

// Utility to compare numbers in the form of the class java.lang.Number
public class NumberComparator {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int compare(Number number1, Number number2) {
		if (((Object) number2).getClass().equals(((Object) number1).getClass())) {
			// both numbers are instances of the same type!
			if (number1 instanceof Comparable) {
				// and they implement the Comparable interface
				return ((Comparable) number1).compareTo(number2);
			}
		}

		// for all different Number types, let's check there double values
		if (number1.doubleValue() < number2.doubleValue())
			return -1;
		if (number1.doubleValue() > number2.doubleValue())
			return 1;

		return 0;
	}
}
