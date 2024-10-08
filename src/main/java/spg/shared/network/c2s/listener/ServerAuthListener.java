package spg.shared.network.c2s.listener;

import spg.shared.network.PacketListener;
import spg.shared.network.c2s.*;

public interface ServerAuthListener extends PacketListener {
    void onLogin(LoginC2SPacket buf);

    void onSignup(SignupC2SPacket buf);

    void onReset(ResetC2SPacket buf);

    void onLogout(LogoutC2SPacket buf);

    void onVerification(VerificationC2SPacket buf);

    void onServerKeyRequest(ServerKeyC2SPacket buf);
}
