package spg.client;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;

public class SocketWriter extends Thread {
    private PrintWriter writer;
    private Socket socket;
    private ClientMain client;

    public SocketWriter(Socket socket, ClientMain client) {
        this.socket = socket;
        this.client = client;

        try  {
            writer = new PrintWriter(
                socket.getOutputStream(), true
            );
            System.out.println("Please enter your name!");
            Scanner scanner = new Scanner(System.in);
            client.setUsername(scanner.nextLine());

            writer.println(client.getUsername()+" has entered the chat!");
        } catch (Exception e) {
            System.err.println("Error creating writer");
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String msg = scanner.nextLine();
            BigInteger target=null; //empfänger

            writer.println(client.getUsername()+ ": "+ msg);
        }
    }
}
