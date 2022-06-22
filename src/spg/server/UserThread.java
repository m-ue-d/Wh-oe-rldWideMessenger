package spg.server;

import java.io.*;
import java.net.Socket;

public class UserThread extends Thread {

    private Socket socket;
    private ServerMain server;
    private PrintWriter writer;
    private BufferedReader reader;

    public UserThread(Socket socket, ServerMain serverMain) {
        this.socket = socket;
        this.server = serverMain;
    }

    @Override
    public void run() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(
                socket.getInputStream()
            ));

            this.writer = new PrintWriter(
                socket.getOutputStream(), true
            );

            while (true) {
                String msg = reader.readLine();
                server.broadcast(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        writer.println(msg);
    }
}
