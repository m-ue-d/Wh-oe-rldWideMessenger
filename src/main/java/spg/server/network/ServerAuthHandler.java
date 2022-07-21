package spg.server.network;

import spg.client.control.network.ClientNetwork;
import spg.server.auth.Email;
import spg.server.database.Database;
import spg.shared.User;
import spg.shared.network.c2s.*;
import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.ClientConnection;
import spg.shared.network.s2c.LoginResponseS2CPacket;
import spg.shared.network.s2c.ResetResponseS2CPacket;
import spg.shared.network.s2c.ServerPublicKeyResponseS2CPacket;
import spg.shared.network.s2c.SignupResponseS2CPacket;
import spg.shared.utility.Validator;

public class ServerAuthHandler implements ServerAuthListener {

    private final ClientConnection connection;

    public ServerAuthHandler(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void onLogin(LoginC2SPacket buf) {
        String email = buf.getEmail();
        String password = buf.getPassword();

        if (!Validator.INSTANCE.isEmailValid(email) || !Validator.INSTANCE.isPasswordValid(password)) {
            connection.send(new LoginResponseS2CPacket(
                LoginResponseS2CPacket.Status.INVALID_CREDENTIALS, null
            ));
            return;
        }

        boolean userExists = Database.INSTANCE.hasEntry(email);
        if (!userExists) {
            connection.send(new LoginResponseS2CPacket(
                LoginResponseS2CPacket.Status.USER_NOT_FOUND, null)
            );
            return;
        }

        ClientNetwork.INSTANCE.sendVerificationCode(
            connection, email, () -> connection.send(new LoginResponseS2CPacket(
                LoginResponseS2CPacket.Status.CODE_SENT, null
            )), () -> {
                User user = ServerNetwork.INSTANCE.loginClient(email, password);
                if (user != null) {
                    ServerNetwork.INSTANCE.mapConnection(user.getId(), connection);
                    connection.send(new LoginResponseS2CPacket(
                        LoginResponseS2CPacket.Status.OK, user
                    ));
                    connection.setListener(
                        new ServerChatHandler(connection)
                    );
                } else {
                    connection.send(new LoginResponseS2CPacket(
                        LoginResponseS2CPacket.Status.INVALID_CREDENTIALS, null
                    ));
                }
            }
        );
    }

    @Override
    public void onSignup(SignupC2SPacket buf) {
        String uname = buf.getUsername();
        String email = buf.getEmail();
        String password = buf.getPassword();

        if (!Validator.INSTANCE.isUnameValid(uname) ||
            !Validator.INSTANCE.isEmailValid(email) ||
            !Validator.INSTANCE.isPasswordValid(password)
        ) {
            connection.send(new SignupResponseS2CPacket(
                SignupResponseS2CPacket.Status.INVALID_CREDENTIALS
            ));
            return;
        }

        boolean userExists = Database.INSTANCE.hasEntry(email);
        if (userExists) {
            connection.send(new SignupResponseS2CPacket(
                SignupResponseS2CPacket.Status.USER_EXISTS
            ));
            return;
        }

        ClientNetwork.INSTANCE.sendVerificationCode(
            connection, email, () -> connection.send(new SignupResponseS2CPacket(
                SignupResponseS2CPacket.Status.CODE_SENT
            )), () -> {
                boolean successful = ServerNetwork.INSTANCE.registerClient(uname, email, password);
                if (successful) {
                    connection.send(new SignupResponseS2CPacket(
                        SignupResponseS2CPacket.Status.OK
                    ));
                } else {
                    connection.send(new SignupResponseS2CPacket(
                        SignupResponseS2CPacket.Status.USER_EXISTS
                    ));
                }
            }
        );
    }

    @Override
    public void onReset(ResetC2SPacket buf) {
        String email = buf.getEmail();
        String newPassword = buf.getNewPassword();

        if (!Validator.INSTANCE.isEmailValid(email)) {
            connection.send(new ResetResponseS2CPacket(
                ResetResponseS2CPacket.Status.INVALID_CREDENTIALS
            ));
            return;
        }

        if (!Validator.INSTANCE.isPasswordValid(newPassword)) {
            connection.send(new ResetResponseS2CPacket(
                ResetResponseS2CPacket.Status.INVALID_CREDENTIALS
            ));
            return;
        }

        boolean userExists = Database.INSTANCE.hasEntry(email);
        if (!userExists) {
            connection.send(new ResetResponseS2CPacket(
                ResetResponseS2CPacket.Status.USER_NOT_FOUND
            ));
            return;
        }

        ClientNetwork.INSTANCE.sendVerificationCode(
            connection, email, () -> connection.send(new ResetResponseS2CPacket(
                ResetResponseS2CPacket.Status.CODE_SENT
            )), () -> {
                boolean successful = ServerNetwork.INSTANCE.resetClientPassword(email, newPassword);
                if (successful) {
                    connection.send(new ResetResponseS2CPacket(
                        ResetResponseS2CPacket.Status.OK
                    ));
                } else {
                    connection.send(new ResetResponseS2CPacket(
                        ResetResponseS2CPacket.Status.FAILED
                    ));
                }
            }
        );
    }

    @Override
    public void onVerification(VerificationC2SPacket buf) {
        if (connection.getVerificationCode() != null) {
            String verificationCode = buf.getVerificationCode();
            if (connection.getVerificationCode().equals(verificationCode)) {
                System.out.println("Verification successful");
                connection.onVerificationCode();
            } else {
                System.out.println("Verification failed");
            }
        }
    }

    @Override
    public void onServerPublicKeyRequest(ServerPublicKeyC2SPacket buf) {
        connection.send(
            new ServerPublicKeyResponseS2CPacket(
                ServerNetwork.INSTANCE.getServerKey().getPublicKey(),
                ServerNetwork.INSTANCE.getServerKey().getModulus()
            )
        );
    }
}
