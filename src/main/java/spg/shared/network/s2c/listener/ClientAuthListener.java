package spg.shared.network.s2c.listener;

import spg.shared.network.PacketListener;
import spg.shared.network.s2c.*;

public interface ClientAuthListener extends PacketListener {
    void onLoginResponse(LoginResponseS2CPacket buf);

    void onSignupResponse(SignupResponseS2CPacket buf);

    void onResetResponse(ResetResponseS2CPacket buf);

    void onServerPublicKeyResponse(ServerKeyResponseS2CPacket buf);
}
