package com.raos.autocode.core.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.raos.autocode.core.util.IOUtil;

public class MessageQueueClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Scanner input = new Scanner(System.in);
		Socket client = new Socket();
		client.connect(new InetSocketAddress(MessageQueueServer.SERVER_ADDRESS));

		OutputStream out = client.getOutputStream();
		InputStream in = client.getInputStream();

		do {

			System.out.println("Write a message to the server:");
			String text = input.nextLine();

			if (text.equals("bye"))
				break;

			out.write(text.getBytes());

			while (in.available() == 0)
				;

			System.out.printf("Server has sent the following message: %s%n", new String(IOUtil.readAll(in)));

		} while (true);

		client.close();
		input.close();
	}

}
