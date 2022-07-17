package spg.server.network;

import spg.server.ServerNetwork;
import spg.shared.User;
import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.ClientConnection;
import spg.shared.network.c2s.LoginC2SPacket;
import spg.shared.network.c2s.ResetC2SPacket;
import spg.shared.network.c2s.SignupC2SPacket;

public class ServerAuthHandler implements ServerAuthListener {

    private final ClientConnection connection;

    public ServerAuthHandler(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void onLogin(LoginC2SPacket buf) {
        String email = buf.getEmail();
        String password = buf.getPassword();

        User user = ServerNetwork.loginClient(email, password);
        if (user != null) {
            ServerNetwork.mapConnection(user.getId(), connection);
            connection.setListener(new ServerChatHandler(connection));
        }
    }

    @Override
    public void onSignup(SignupC2SPacket buf) {
        String uname = buf.getUsername();
        String email = buf.getEmail();
        String password = buf.getPassword();

        ServerNetwork.registerClient(uname, email, password);
    }

    @Override
    public void onReset(ResetC2SPacket buf) {
        String email = buf.getEmail();

        ServerNetwork.resetClientPassword(email);
    }
}
