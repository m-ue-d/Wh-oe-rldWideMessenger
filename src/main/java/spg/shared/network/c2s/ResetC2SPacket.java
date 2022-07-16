package spg.shared.network.c2s;

import spg.shared.listeners.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

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
