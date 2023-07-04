package com.raos.autocode.core.util;

import java.io.IOException;
import java.io.InputStream;

public class IOUtil {

	public static byte[] readAll(InputStream input) throws IOException {
		return input.readNBytes(input.available());
	}
}
