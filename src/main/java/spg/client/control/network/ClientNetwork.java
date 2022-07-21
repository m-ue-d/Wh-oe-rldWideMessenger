package spg.client.control.network;

import io.netty.channel.Channel;
import spg.server.auth.Email;
import spg.shared.User;
import spg.shared.network.ClientConnection;
import spg.shared.network.c2s.*;
import spg.shared.security.AES;
import spg.shared.utility.Validator;

import java.math.BigInteger;

public final class ClientNetwork {

    public static final ClientNetwork INSTANCE = new ClientNetwork();

    public ClientConnection connection;
    public byte[] symmetricKey;

    public BigInteger serverPublicKey;
    public BigInteger serverModulus;

    public void initialize() {
        System.out.println("Initializing client network...");
    }

    public void shutdown() {
        System.out.println("Shutting down client network...");
        Channel channel = connection.getChannel();
        if (channel != null) {
            channel.close().syncUninterruptibly();
        }
    }

    public void sendVerificationCode(ClientConnection connection, String email, Runnable onCodeSent, Runnable onSuccess) {
        String verificationCode = Email.INSTANCE.genCode();
        connection.setVerificationCode(verificationCode);
        System.out.println("Sending verification code: " + verificationCode);
        Email.INSTANCE.sendMail(email, verificationCode);
        onCodeSent.run();
        connection.setOnVerificationSuccess(onSuccess);
    }

    public void signup(String uname, String email, String password) {
        System.out.println("Signing up...");
        String username = uname.replace(" ", "_").trim();
        String address = email.trim();

        if (!Validator.INSTANCE.isUnameValid(username)) {
            System.out.println("Invalid username");
            return;
        }

        if (!Validator.INSTANCE.isEmailValid(address)) {
            System.out.println("Invalid email");
            return;
        }

        if (!Validator.INSTANCE.isPasswordValid(password)) {
            System.out.println("Invalid password");
            return;
        }

        connection.send(
            new SignupC2SPacket(
                username, address, password
            )
        );
    }

    public void login(String email, String password) {
        System.out.println("Logging in...");
        String address = email.trim();

        if (!Validator.INSTANCE.isEmailValid(address)) {
            System.out.println("Invalid email");
            return;
        }

        if (!Validator.INSTANCE.isPasswordValid(password)) {
            System.out.println("Invalid password");
            return;
        }

        // shared communication key
        symmetricKey = AES.INSTANCE.genKey();

        connection.send(
            new LoginC2SPacket(
                address, password, symmetricKey, serverPublicKey, serverModulus
            )
        );
    }

    public void reset(String email, String newPassword) {
        System.out.println("Resetting Account...");
        String address = email.trim();

        if (!Validator.INSTANCE.isEmailValid(address)) {
            System.out.println("Invalid email");
            return;
        }

        if (!Validator.INSTANCE.isPasswordValid(newPassword)) {
            System.out.println("Invalid password");
            return;
        }

        connection.send(
            new ResetC2SPacket(address, newPassword)
        );
    }

    public void verify(String verificationCode) {
        if (!Validator.INSTANCE.isVerificationCodeValid(verificationCode)) {
            System.out.println("Invalid verification code");
            return;
        }

        System.out.println("Verifying Account with code: " + verificationCode + "...");

        connection.send(
            new VerificationC2SPacket(verificationCode)
        );
    }
}
