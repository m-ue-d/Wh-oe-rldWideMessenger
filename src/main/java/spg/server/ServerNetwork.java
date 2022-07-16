package spg.server;

import spg.server.auth.PBKDF2;
import spg.server.database.Database;
import spg.shared.User;
import spg.shared.network.ClientConnection;
import spg.shared.security.Keychain;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServerNetwork {

    private static final Map<Integer, ClientConnection> clients = new HashMap<>();
    private static Keychain keychain;

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
//        keychain = RSA.genKeyPair_plusN();
        System.out.println();
        timer.stop();
    }

    /**
     * Maps a client id to the communication channel.
     * @param id The client's uid.
     *
     * TODO: get instance from connections list @see {@link Server childHandler}
     */
    public static void mapClient(int id) {
        ClientConnection connection = null;
        clients.put(id, connection);
    }

    public static void registerClient(String uname, String email, String password) {
        System.out.println("Signup request for " + uname + " with email " + email + " and password " + password);
        Database.INSTANCE.addEntry(uname, email, password);
        System.out.println("Successfully registered user: " + uname + "!");
    }

    public static User loginClient(String email, String password) {
        System.out.println("Login request for " + email + " with password " + password);
        User user = Database.INSTANCE.getEntry(email);
        if (user != null && PBKDF2.INSTANCE.verify(
            password.toCharArray(), Objects.requireNonNull(user.getPassword())
        )) {
            System.out.println("Successfully logged in user: " + user.getUname() + "!");
            return user;
        }
        return null;
    }

    public static void resetClientPassword(String email) {
        System.out.println("Account reset request for " + email);
        User user = Database.INSTANCE.getEntry(email);
        if (user != null) {
            System.out.println("Successfully reset password for user: " + user.getUname() + "!");
        }
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
        //  solve using an api request to the server!
        return Database.INSTANCE.getEntry(target);
    }

    /**
     * Returns the public key of the server. This key is used to encrypt any messages that need to be handled by the server itself.
     * @return The public key of the server.
     */
    public static BigInteger getServerKey() {
        return keychain.getPublicKey();
    }
}
