package com.raos.autocode.core.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.raos.autocode.core.annotations.ToDo;

@ToDo(description = "Add multithreading support including the management of locks")
public class ThreadTest {
	public static void main(String[] args) throws Exception {

		// ------------------------------------------------
		// TESTING FOR WAITING AND RECIEVING
		// ------------------------------------------------
		// Threading test
		
		collectionsTest(ConcurrentHashMap.newKeySet());

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

	public static void collectionsTest(Collection<Integer> value) {

		for (int i = 0; i < 10; i++)
			value.add(i);

		for (int vv : value) {
			System.out.println("Hello World: " + vv);
			value.remove(vv);
			System.out.println(value);
		}

		System.out.println(value);
	}
}
