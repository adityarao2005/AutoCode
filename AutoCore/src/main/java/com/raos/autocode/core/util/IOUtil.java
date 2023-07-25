package com.raos.autocode.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class IOUtil {

	public static byte[] readAll(InputStream input) throws IOException {
		return input.readNBytes(input.available());
	}

	public static void copy(InputStream in, OutputStream out) throws IOException {
		out.write(readAll(in));
	}

	public static char[] readAll(Reader reader) throws IOException {
		StringWriter writer = new StringWriter();
		copy(reader, writer);
		return writer.toString().toCharArray();
	}

	public static void copy(Reader reader, Writer writer) throws IOException {
		reader.transferTo(writer);
	}
}
