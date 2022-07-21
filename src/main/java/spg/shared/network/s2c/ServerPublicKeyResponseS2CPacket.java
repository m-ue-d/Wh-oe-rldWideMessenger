package spg.shared.network.s2c;

import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientAuthListener;

import java.math.BigInteger;

/**
 * A packet sent by the client to the server to reset their password.
 */
public final class ServerPublicKeyResponseS2CPacket implements Packet<ClientAuthListener> {

    private final BigInteger publicKey;
    private final BigInteger modulus;

    public ServerPublicKeyResponseS2CPacket(BigInteger publicKey, BigInteger modulus) {
        this.publicKey = publicKey;
        this.modulus = modulus;
    }

    public ServerPublicKeyResponseS2CPacket(PacketBuf buf) {
        this.publicKey = buf.readRSAKey();
        this.modulus = buf.readModulus();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeRSAKey(publicKey);
        buf.writeModulus(modulus);
    }

    @Override
    public void apply(ClientAuthListener listener) {
        listener.onServerPublicKeyResponse(this);
    }

    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getModulus() {
        return modulus;
    }
}
