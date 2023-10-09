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
	 * 
	 * @param length
	 * @return
	 */
	public default boolean doRead(byte[] content) {

		// while there is still more, read
		int i = 0;
		while (i < content.length && hasNext()) {
			content[i] = doRead();
			i++;
		}

		return hasNext();

	}

	/**
	 * 
	 * @return
	 */
	public default Stream<Byte> streamContent() {
		return Stream.iterate(doRead(), t -> this.hasNext(), t -> doRead());
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
