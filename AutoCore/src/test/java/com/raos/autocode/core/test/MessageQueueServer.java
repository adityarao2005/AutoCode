package com.raos.autocode.core.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import com.raos.autocode.core.util.IOUtil;

public class MessageQueueServer {
	public static final int SERVER_ADDRESS = 8080;

	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(System.in);
		ServerSocket server = new ServerSocket(SERVER_ADDRESS);

		System.err.println("Server opened");

		Socket clientSocket = server.accept();

		System.err.println("Client connected");

		InputStream in = clientSocket.getInputStream();
		OutputStream out = clientSocket.getOutputStream();

		int timeoutTime = 60000;

		outer: while (clientSocket.isConnected()) {

			long startTime = System.currentTimeMillis();
			long currentTime = startTime;

			while (in.available() == 0) {
				if (currentTime - startTime > timeoutTime) {
					System.err.println("Timeout");
					out.write("timeout".getBytes());
					break outer;
				}

				currentTime = System.currentTimeMillis();
			}

			System.out.printf("Client says: %s%n", new String(IOUtil.readAll(in)));

			System.out.println("Write a message to the client: ");
			byte[] text = input.nextLine().getBytes();

			if (clientSocket.isClosed())
				System.err.println("Peer is closed");

			out.write(text);
		}

		System.err.println("Client disconnected");

		input.close();
		server.close();
	}

}
