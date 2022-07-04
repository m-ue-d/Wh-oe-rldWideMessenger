package spg.server;

import io.netty.channel.ChannelHandlerContext;
import spg.server.database.Database;
import spg.shared.User;
import spg.shared.security.RSA;
import spg.shared.security.SecurityKeychain;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class ServerNetwork {

    private static final Map<Integer, ChannelHandlerContext> clients = new HashMap<>();
    private static SecurityKeychain keychain;

    /**
     * Initializes the server by generating a new RSA keypair.
     */
    public static void initialize() {
        System.out.println("Initializing server network...");
        System.out.println("Generating keys...");
        double start = System.currentTimeMillis();
        Thread timer = new Thread(() -> {
            try {
                while (true) {
                    double elapsed = System.currentTimeMillis() - start;
                    System.out.print("\rTime elapsed: " + elapsed / 1000.0 + "s");
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Time");
        timer.start();
        keychain = RSA.genKeyPair_plusN();
        System.out.println();
        timer.stop();
    }

    /**
     * Registers a new client and maps the uid to the communication channel.
     * @param id The client's uid.
     * @param ctx The communication channel between the client and the server.
     */
    public static void registerClient(int id, ChannelHandlerContext ctx) {
        clients.put(id, ctx);
    }

    /**
     * Gets a user from the database as long as the requester is friends with the user.
     * @param sender The requesters' uid.
     * @param target The target's uid.
     * @return The user if the requester is friends with the target, null otherwise.
     */
    public static User getUser(int sender, int target) {
        // Todo: check if sender is friends with target
        //  otherwise you could query any user in the network (reduced privacy)
        return Database.Companion.getEntry(target);
    }

    /**
     * Returns the public key of the server. This key is used to encrypt any messages that need to be handled by the server itself.
     * @return The public key of the server.
     */
    public static BigInteger getServerKey() {
        return keychain.getPublicKey();
    }
}
