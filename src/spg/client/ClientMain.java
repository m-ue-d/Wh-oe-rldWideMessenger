package spg.client;

import java.math.BigInteger;
import java.net.Socket;

public class ClientMain {

    private final String host;
    private final int port;
    private String username="anonymus";

    private BigInteger privateKey;
    public final BigInteger publicKey;
    public final BigInteger N;

    public static void main(String[] args) {
        new ClientMain("boiisserver2020.ddns.net", 6666).start();
    }

    public ClientMain(String host, int port) {
        BigInteger[] temp= RSA.genKeyPair_plusN();
        this.privateKey=temp[0];
        this.publicKey=temp[1];
        this.N= temp[2];


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
    public byte[] decode(String msg){  //key==d
        return (new BigInteger(msg.getBytes())).modPow(privateKey,N).toByteArray();
    }



    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
