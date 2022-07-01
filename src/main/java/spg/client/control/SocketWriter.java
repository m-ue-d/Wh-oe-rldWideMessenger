package spg.client.control;

import spg.server.Packet;
import spg.server.PacketByteBuffer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketWriter extends Thread {
	private ObjectOutputStream writer;
	private Socket socket;	//serverSocket
	private Client client;

	public SocketWriter(Socket socket, Client client) {
		this.socket = socket;
		this.client = client;
	}


	@Override
	public void run() {
		try  {
			writer = new ObjectOutputStream(
				socket.getOutputStream()
			);
		} catch (Exception e) {
			System.err.println("Error creating output stream");
		}

		var scanner = new Scanner(System.in);

		while (true) {

			int[] ids = {0};	/*  <- NUR TEST
			-- Ich such noch ne möglichkeit, die Publickeys der anderen User zu bekommen. Die ids hier sollen durch ein seperates Textfeld im View eingegeben werden. Muss das noch gut mit Fabi besprechen.*/


			var input = scanner.nextLine();
			var packet = new Packet("UserMessage", new PacketByteBuffer.Builder()
				.writeMessage(input)
				.build(ids)
			);

			try {
				writer.writeObject(
					packet
				);
			} catch (IOException e) {
				System.err.println("Could not write to socket," +
					"the object may not be serializable");
				break;
			}
		}
		
		scanner.close();
	}
}
