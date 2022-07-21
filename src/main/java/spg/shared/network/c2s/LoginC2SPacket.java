package spg.shared.network.c2s;

import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

import java.math.BigInteger;

/**
 * A packet sent by the client to the server to login.
 */
public class LoginC2SPacket implements Packet<ServerAuthListener> {
    private final String email;
    private final String password;
    private final byte[] symmetricKey;

    private final BigInteger publicKey;
    private final BigInteger modulus;

    public LoginC2SPacket(String email, String password, byte[] symmetricKey, BigInteger publicKey, BigInteger modulus) {
        this.email = email;
        this.password = password;
        this.symmetricKey = symmetricKey;
        this.publicKey = publicKey;
        this.modulus = modulus;
    }

    public LoginC2SPacket(PacketBuf buf) {
        this.email = buf.readString();
        this.password = buf.readString();
        this.symmetricKey = buf.readAESKey();

        // Null, weil der server seine eigenen keys eh kennt
        this.publicKey = null;
        this.modulus = null;
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeString(email);
        buf.writeString(password);
        buf.writeAESKey(symmetricKey);
//        buf.encrypt(publicKey, modulus);
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

    public byte[] getSymmetricKey() {
        return symmetricKey;
    }
}
