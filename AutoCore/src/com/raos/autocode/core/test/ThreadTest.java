package com.raos.autocode.core.test;

import com.raos.autocode.core.annotations.ToDo;
import com.raos.autocode.core.property.Pointer;

@ToDo(description = "Add multithreading support including the management of locks", methods = "")
public class ThreadTest {
	public static void main(String[] args) throws Exception {

		// ------------------------------------------------
		// TESTING FOR WAITING AND RECIEVING
		// ------------------------------------------------
		// Threading test
		
		
		
		System.out.println("Hello World");

		Object lock = new Object();

		System.out.println("Going to wait for 5 seconds");

		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(5000);

				synchronized (lock) {
					lock.notify();
				}
			} catch (Exception e) {
				System.err.println("Could not wait for 5 seconds");
				return;
			}
		});

		thread.start();

		synchronized (lock) {
			lock.wait();
		}

		System.out.println("Waited 5 seconds!");

	}
}
