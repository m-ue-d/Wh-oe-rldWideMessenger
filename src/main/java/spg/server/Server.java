package spg.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private final int port;
	private final ArrayList<UserThread> connectedUsers = new ArrayList<>();

	public static void main(String[] args) {
		new Server(8080).start();
	}

	public Server(int port) {
		this.port = port;
	}

	public void start() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Listening on port: " + port);
			//noinspection InfiniteLoopStatement
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("User connected: " + socket.getInetAddress());

				UserThread connectedUser = new UserThread(socket, this);
				connectedUsers.add(connectedUser);
				connectedUser.start();
			}
		} catch (Exception e) {
			System.err.println("Could not start server: " + e.getMessage());
		}
	}

	public void broadcast(Packet packet) {
		for (UserThread connectedUser : connectedUsers) {
			ServerNetwork.sendPacket(connectedUser, packet);
		}
	}
}
