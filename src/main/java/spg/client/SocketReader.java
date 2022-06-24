package spg.client;

import spg.server.Packet;

import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketReader extends Thread {
    private ObjectInputStream reader;
    private Socket socket;
    private Client client;

    public SocketReader(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run() {
        try {
            reader = new ObjectInputStream(
                socket.getInputStream()
            );
        } catch (Exception e) {
            System.err.println("Error creating object input stream");
        }

        while (true) {
            try {
                Packet obj = (Packet) reader.readObject();
                ClientNetwork.handlePacket(client, obj);
            } catch (Exception e) {
                System.err.println("Error reading packet from server");
                break;
            }
        }
    }
}
