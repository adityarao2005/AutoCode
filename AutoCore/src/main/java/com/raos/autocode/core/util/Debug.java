package com.raos.autocode.core.util;

import java.lang.StackWalker.Option;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * Basic utility class for debugging
 * 
 * @author aditya
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "2023-09-27")
public interface Debug {

	/**
	 * Log a message
	 * 
	 * @param message
	 */
	public static void logInfo(String message) {
		log(message, Level.INFO);
	}
	
	/**
	 * Log a message
	 * 
	 * @param message
	 */
	public static void logWarning(String message) {
		log(message, Level.WARNING);
	}
	
	/**
	 * Log a message
	 * 
	 * @param message
	 */
	public static void logError(String message) {
		log(message, Level.SEVERE);
	}
	
	/**
	 * Log a message
	 * 
	 * @param message
	 */
	public static void logConfig(String message) {
		log(message, Level.CONFIG);
	}
	
	/**
	 * Log a message with level
	 * 
	 * @param message
	 * @param level
	 */
	public static void log(String message, Level level) {
		log(message, level, new Object[] {});
	}

	/**
	 * Log a message with level and params
	 * 
	 * @param message
	 * @param level
	 * @param params
	 */
	public static void log(String message, Level level, Object... params) {
		String realMessage = String.format(message, params);
		// Get caller class by walking down the stack
		// Get instance of stack walker
		Class<?> caller = StackWalker.getInstance(Option.RETAIN_CLASS_REFERENCE)
				// walk by getting a stream
				.walk(s ->
				// for the stream, drop while the class is this class and get the first result
				s.dropWhile(sf -> sf.getClass() == Debug.class).findFirst())
				// and then get the class
				.get().getClass();

		// Log using default logger
		Logger.getLogger(caller.getName()).log(level, realMessage);
	}
}
