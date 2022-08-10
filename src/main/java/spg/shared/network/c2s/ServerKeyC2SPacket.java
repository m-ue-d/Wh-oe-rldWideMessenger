package spg.shared.network.c2s;

import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.c2s.listener.ServerAuthListener;

/**
 * A packet sent by the client to the server to request its public key.
 */
public final class ServerKeyC2SPacket implements Packet<ServerAuthListener> {

    public ServerKeyC2SPacket() {

    }

    public ServerKeyC2SPacket(PacketBuf buf) {

    }

    @Override
    public void write(PacketBuf buf) {

    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onServerKeyRequest(this);
    }
}
