package spg.shared.network.c2s;

import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to reset their password.
 */
public final class ResetC2SPacket implements Packet<ServerAuthListener> {
    private final String email;
    private final String newPassword;

    public ResetC2SPacket(String email, String newPassword) {
        this.email = email;
        this.newPassword = newPassword;
    }

    public ResetC2SPacket(PacketBuf buf) {
        this.email = buf.readString();
        this.newPassword = buf.readString();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeString(email);
        buf.writeString(newPassword);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onReset(this);
    }

    public String getEmail() {
        return email;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
