package com.raos.autocode.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Utilities for I/O
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-07-05")
public interface IOUtil {

	/**
	 * Read all bytes from input stream
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	public static byte[] readAll(InputStream input) throws IOException {
		return input.readNBytes(input.available());
	}

	/**
	 * Copy from input to output
	 * 
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copy(InputStream in, OutputStream out) throws IOException {
		// Create buffer
		int buf;
		// Read byte and check if not -1, if not write, if -1 then stop
		while ((buf = in.read()) != -1) {
			out.write(buf);
		}
	}

	/**
	 * Read all from reader
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	public static String readAll(Reader reader) throws IOException {
		StringWriter writer = new StringWriter();
		copy(reader, writer);
		return writer.toString();
	}

	/**
	 * Copy from reader to writer
	 * @param reader
	 * @param writer
	 * @throws IOException
	 */
	public static void copy(Reader reader, Writer writer) throws IOException {
		reader.transferTo(writer);
	}
}
