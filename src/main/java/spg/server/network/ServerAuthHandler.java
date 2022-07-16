package spg.server.network;

import spg.server.ServerNetwork;
import spg.shared.User;
import spg.shared.listeners.ServerAuthListener;
import spg.shared.network.ClientConnection;
import spg.shared.network.c2s.LoginC2SPacket;
import spg.shared.network.c2s.ResetC2SPacket;
import spg.shared.network.c2s.SignupC2SPacket;

public class ServerAuthHandler implements ServerAuthListener {

    @Override
    public void onLogin(LoginC2SPacket buf) {
        String email = buf.getEmail();
        String password = buf.getPassword();

        User user = ServerNetwork.loginClient(email, password);
        if (user != null) {
            ServerNetwork.mapClient(user.getId());
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
