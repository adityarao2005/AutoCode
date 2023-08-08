package com.raos.autocode.core.util;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class ProxyUtil {

	// Allows for private access into interfaces and grabs default methods
	public static Object handleDefaultMethod(Object proxy, Class<?> declaringClass, Method method, Object... args)
			throws IllegalAccessException, Throwable {
		// Get private lookup
		return MethodHandles.privateLookupIn(declaringClass, MethodHandles.lookup())
				// Unreflect special
				.unreflectSpecial(method, declaringClass)
				// Bind to the proxy
				.bindTo(proxy)
				// Invoke with args
				.invokeWithArguments(args);
	}
}
