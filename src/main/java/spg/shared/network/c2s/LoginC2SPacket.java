package spg.shared.network.c2s;

import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to login.
 */
public class LoginC2SPacket implements Packet<ServerAuthListener> {
    private final String email;
    private final String password;

    public LoginC2SPacket(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginC2SPacket(PacketBuf buf) {
        this.email = buf.readString();
        this.password = buf.readString();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeString(email);
        buf.writeString(password);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onLogin(this);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
