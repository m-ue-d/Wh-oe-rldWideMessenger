package spg.shared.network.c2s;

import spg.shared.network.c2s.listener.ServerAuthListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.security.RSA;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * A packet sent by the client to the server to login.
 */
public class LoginC2SPacket implements Packet<ServerAuthListener> {
    private byte[] email;
    private byte[] password;
    private final BigInteger publicKey;
    private final BigInteger modulus;

    public LoginC2SPacket(String email, String password, BigInteger publicKey, BigInteger modulus) {
        this.email = email.getBytes();
        this.password = password.getBytes();
        this.publicKey = publicKey;
        this.modulus = modulus;
    }

    public LoginC2SPacket(PacketBuf buf) {
        this.email= buf.readBytes();
        this.password = buf.readBytes();

        // Null, weil der server seine eigenen keys eh kennt
        this.publicKey = null;
        this.modulus = null;
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeBytesEncryptRSA(new String(email),publicKey,modulus);
        buf.writeBytesEncryptRSA(new String(password),publicKey,modulus);
    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onLogin(this);
    }

    public void decrypt(BigInteger privateKey, BigInteger modulus){
        this.email= RSA.INSTANCE.decrypt(this.email,privateKey,modulus);
        this.password= RSA.INSTANCE.decrypt(this.password,privateKey,modulus);
    }
    public String getEmail() {
        return new String(this.email);
    }

    public String getPassword() {
        return new String(this.password);
    }
}
