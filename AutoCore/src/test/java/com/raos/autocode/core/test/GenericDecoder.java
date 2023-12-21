package com.raos.autocode.core.test;

import java.util.List;
import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

// TODO: Add more ways to get application contexts (xml, json, yml, properties)
// TODO: Use this as an alternative to @BeanProperty(type = <>)
public class GenericDecoder {
	public static void main(String[] args) throws Exception {
		Type type = GenericDecoder.class.getMethod("type").getGenericReturnType();

		System.out.println(type);
		System.out.println(type.getClass());

		if (type instanceof ParameterizedType) {
			ParameterizedType params = (ParameterizedType) type;

			System.out.println(params.getOwnerType());
			System.out.println(params.getRawType());
			System.out.println("Types");
			
			for (Type typer : params.getActualTypeArguments()) {
				System.out.print(typer + ", ");
			}
		}
	}

	public List<String> type() {
		return null;
	}
}
