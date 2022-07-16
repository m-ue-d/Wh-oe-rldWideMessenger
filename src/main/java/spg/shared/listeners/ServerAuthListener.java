package spg.shared.listeners;

import spg.shared.network.ClientConnection;
import spg.shared.network.PacketListener;
import spg.shared.network.c2s.LoginC2SPacket;
import spg.shared.network.c2s.ResetC2SPacket;
import spg.shared.network.c2s.SignupC2SPacket;

public interface ServerAuthListener extends PacketListener {
    void onSignup(SignupC2SPacket buf);

    void onLogin(LoginC2SPacket buf);

    void onReset(ResetC2SPacket buf);
}
