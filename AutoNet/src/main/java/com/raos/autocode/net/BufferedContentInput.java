package com.raos.autocode.net;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * 
 * @author aditya
 * @date Oct. 9, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Oct. 9, 2023")
public interface BufferedContentInput extends BinaryContentInput {

	/**
	 * Reads content in form of a chunck or a buffer
	 * 
	 * @param length
	 * @return
	 */
	public boolean doRead(byte[] content);

	/**
	 * Converts to Stream of Integers or bytes
	 * 
	 * @return
	 */
	public default Stream<Integer> streamContent() {
		return Stream.generate(this::doRead).takeWhile(i -> i != -1);
	}

	/**
	 * 
	 * @param length
	 * @return
	 */
	public default Stream<byte[]> streamChunked(int length) {
		// Stream an iterator
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(new Iterator<byte[]>() {
			private byte[] current;

			@Override
			public boolean hasNext() {
				return doRead(current);
			}

			@Override
			public byte[] next() {
				return current;
			}
		}, Spliterator.ORDERED), false);
	}

}
