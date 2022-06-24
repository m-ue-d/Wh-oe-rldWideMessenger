package spg.client;

import java.net.Socket;

public class Client {
	private final String host;
	private final int port;

	public static void main(String[] args) {
		ClientNetwork.initialize();
//        new Client("192.164.135.65", 6666).start();
		new Client("localhost", 8080).start();
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		try {
			Socket socket = new Socket(this.host, this.port);
			System.out.println("Connected to: " + socket.getInetAddress());

			new SocketReader(socket, this).start();
			new SocketWriter(socket, this).start();
		} catch (Exception e) {
			System.err.println("Could not connect to server");
		}
	}
}

