package com.raos.autocode.core.test;

//import jakarta.xml.bind.*;
import javax.xml.transform.stream.*;
import javax.xml.namespace.*;
import java.io.*;
import java.util.*;

public class JAXBTester {
	public static class Book {
		private int id;
		private String name;
		private String author;

		public void setId(int id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getAuthor() {
			return author;
		}

		public String toString() {
			return String.format("Book [name=%s, id=%d, author=%s]", name, id, author);
		}
	}

//	public static <T> Optional<String> objectToXMLString(T value, Class<T> clazz, String rootElement) {
//		StringWriter sw = new StringWriter();
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			JAXBElement<T> jaxbElement = new JAXBElement<>(new QName(rootElement), clazz, value);
//			jaxbMarshaller.marshal(jaxbElement, sw);
//			return Optional.of(sw.toString());
//		} catch (JAXBException e) {
//			throw new RuntimeException(String.format("XML to String Conversion exception:%s", e.getMessage()), e);
//		}
//	}
//
//	public static <T> Optional<T> xmlStringToObject(String value, Class<T> clazz) {
//		try {
//			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
//			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//			InputStream is = new ByteArrayInputStream(value.getBytes());
//			JAXBElement<T> object = unmarshaller.unmarshal(new StreamSource(is), clazz);
//			return Optional.of(object.getValue());
//		} catch (JAXBException e) {
//			throw new RuntimeException(String.format("XML to String Conversion exception:%s", e.getMessage()), e);
//		}
//	}
//
//	public static void main(String[] args) throws Exception {
//
//		Book book = new Book();
//		book.setId(1);
//		book.setName("Book1");
//		book.setAuthor("Author1");
//
//		System.out.println(book);
//
//		System.out.println("Marshalling...");
//
//		String value = objectToXMLString(book, Book.class, "book").get();
//
//		System.out.println(value);
//
//		System.out.println("Unmarshalling...");
//
//		book = xmlStringToObject(value, Book.class).get();
//
//		System.out.println(book);
//
//	}
}