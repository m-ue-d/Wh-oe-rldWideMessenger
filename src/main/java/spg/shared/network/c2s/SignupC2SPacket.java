package spg.shared.network.c2s;

import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to signup on the platform.
 */
public final class SignupC2SPacket implements Packet<ServerAuthListener> {
    private final String username;
    private final String email;
    private final String password;

    public SignupC2SPacket(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public SignupC2SPacket(PacketBuf buf) {
        this.username = buf.readString();
        this.email = buf.readString();
        this.password = buf.readString();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeString(username);
        buf.writeString(email);
        buf.writeString(password);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onSignup(this);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
