package com.raos.autocode.net;

import com.raos.autocode.core.annotations.ClassPreamble;

/**
 * 
 * @author aditya
 * @date Oct. 9, 2023
 *
 */
@ClassPreamble(author = "Aditya Rao", date = "Oct. 9, 2023")
public abstract class Server<T> implements Runnable, NetBridge<T> {
	private int port;

	public Server(int port) {
		this.port = port;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
