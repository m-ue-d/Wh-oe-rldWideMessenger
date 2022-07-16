package spg.client.control;

import io.netty.channel.Channel;
import spg.shared.network.ClientConnection;
import spg.shared.network.c2s.LoginC2SPacket;
import spg.shared.network.c2s.ResetC2SPacket;
import spg.shared.network.c2s.SignupC2SPacket;
import spg.shared.utility.Validator;

public class ClientNetwork {
    public static ClientConnection connection;

    public static void initialize() {
        Client.main("localhost", "8080");
    }

    public static void signup(String uname, String email, String password) {
        System.out.println("Signing up...");
        String username = uname.replace(" ", "_").trim();
        String address = email.trim();

        if (!Validator.isUnameValid(username)) {
            System.out.println("Invalid username");
            return;
        }

        if (!Validator.isEmailValid(address)) {
            System.out.println("Invalid email");
            return;
        }

        if (!Validator.isPasswordValid(password)) {
            System.out.println("Invalid password");
            return;
        }

        connection.send(
            new SignupC2SPacket(username, address, password)
        );
    }

    public static void login(String email, String password) {
        System.out.println("Logging in...");
        String address = email.trim();

        if (!Validator.isEmailValid(address)) {
            System.out.println("Invalid email");
            return;
        }

        if (!Validator.isPasswordValid(password)) {
            System.out.println("Invalid password");
            return;
        }

        connection.send(
            new LoginC2SPacket(address, password)
        );
    }

    public static void reset(String email) {
        System.out.println("Resetting Account...");
        String address = email.trim();

        if (!Validator.isEmailValid(address)) {
            System.out.println("Invalid email");
            return;
        }

        connection.send(
            new ResetC2SPacket(address)
        );
    }
}
