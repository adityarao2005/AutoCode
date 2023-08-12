package com.raos.autocode.core.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class MethodHandleUtils {

	public static <T> T toLambda(Class<T> lambdaClass, MethodHandle methodHandle) {
		return MethodHandleProxies.asInterfaceInstance(lambdaClass, methodHandle);
	}

	public static MethodHandle fromMethod(Method method, boolean privateAccess) throws IllegalAccessException {
		return (privateAccess ? MethodHandles.privateLookupIn(method.getDeclaringClass(), MethodHandles.lookup())
				: MethodHandles.lookup()).unreflect(method);
	}

	public static MethodHandle setCallerObject(MethodHandle methodHandle, Object obj) {
		return methodHandle.bindTo(obj);
	}

	public static MethodType toMethodType(Method method) {
		return MethodType.methodType(method.getReturnType(), method.getParameterTypes());
	}
}
