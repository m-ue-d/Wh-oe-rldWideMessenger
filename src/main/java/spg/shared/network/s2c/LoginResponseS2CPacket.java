package spg.shared.network.s2c;

import spg.shared.User;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientAuthListener;

/**
 * A packet sent by the server to the client to tell them if they were successful or not.
 */
public class LoginResponseS2CPacket implements Packet<ClientAuthListener> {

    public enum Status {
        OK, CODE_SENT, INVALID_CREDENTIALS, USER_NOT_FOUND
    }

    private final Status status;
    private final User user;

    public LoginResponseS2CPacket(Status status, User user) {
        this.status = status;
        this.user = user;
    }

    public LoginResponseS2CPacket(PacketBuf buf) {
        this.status = buf.readEnum(Status.class);
        this.user = buf.readUser();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeEnum(status);
        buf.writeUser(user);
    }

    @Override
    public void apply(ClientAuthListener listener) {
        listener.onLoginResponse(this);
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }
}
