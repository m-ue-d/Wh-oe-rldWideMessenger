package spg.shared.network.c2s;

import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.c2s.listener.ServerAuthListener;

/**
 * A packet sent by the client to the server to reset their password.
 */
public final class ServerPublicKeyC2SPacket implements Packet<ServerAuthListener> {

    public ServerPublicKeyC2SPacket() {

    }

    public ServerPublicKeyC2SPacket(PacketBuf buf) {

    }

    @Override
    public void write(PacketBuf buf) {

    }

    @Override
    public void apply(ServerAuthListener listener) {
        listener.onServerPublicKeyRequest(this);
    }
}
