package spg.server;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {

	private Socket socket;
	private Server server;
	private ObjectOutputStream writer;
	private ObjectInputStream reader;

	public UserThread(Socket socket, Server serverMain) {
		this.socket = socket;
		this.server = serverMain;
	}

	@Override
	public void run() {
		try {
			this.reader = new ObjectInputStream(
				socket.getInputStream()
			);

			this.writer = new ObjectOutputStream(
				socket.getOutputStream()
			);

			while (true) {
				Packet packet = (Packet) reader.readObject();
				server.broadcast(packet);
			}
		} catch (Exception e) {
			System.err.println("Error broadcasting packet");
		}
	}

	public void send(Packet packet) {
		try {
			writer.writeObject(packet);
		} catch (IOException e) {
			System.err.println("Error sending packet to client," +
				" the client may have disconnected");
		}
	}
}
