package spg.shared.network.c2s;

import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to reset their password.
 */
public final class ResetC2SPacket implements Packet<ServerAuthListener> {
    private final String email;

    public ResetC2SPacket(String email) {
        this.email = email;
    }

    public ResetC2SPacket(PacketBuf buf) {
        this.email = buf.readString();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeString(email);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onReset(this);
    }

    public String getEmail() {
        return email;
    }
}
