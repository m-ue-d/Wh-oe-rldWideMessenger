package spg.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private ClientMain client;

    public SocketReader(Socket socket, ClientMain client) {
        this.socket = socket;
        this.client = client;

        try {
            this.reader = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream()
                )
            );
        } catch (Exception e) {
            System.err.println("Error creating reader");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = reader.readLine();

                System.out.println(msg);
            } catch (Exception e) {
                System.err.println("Error reading from server");
                e.printStackTrace();
                break;
            }
        }
    }
}
