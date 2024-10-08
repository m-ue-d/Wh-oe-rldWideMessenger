package spg.shared.network.c2s;

import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.security.RSA;

import java.math.BigInteger;

/**
 * A packet sent by the client to the server to login.
 */
public class LogoutC2SPacket implements Packet<ServerAuthListener> {

    public enum Reason {
        LOGOUT, CLIENT_QUIT
    }

    private final Reason reason;

    public LogoutC2SPacket(Reason reason) {
        this.reason = reason;
    }

    public LogoutC2SPacket(PacketBuf buf) {
        this.reason = buf.readEnum(Reason.class);
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeEnum(reason);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onLogout(this);
    }

    public Reason getReason() {
        return reason;
    }
}
