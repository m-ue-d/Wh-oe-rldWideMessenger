package spg.shared.network.c2s;

import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.c2s.listener.ServerAuthListener;

/**
 * A packet sent by the client to the server to verify their email address.
 */
public final class VerificationC2SPacket implements Packet<ServerAuthListener> {
    private final String verificationCode;

    public VerificationC2SPacket(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public VerificationC2SPacket(PacketBuf buf) {
        this.verificationCode = buf.readString();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeString(verificationCode);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onVerification(this);
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
