package spg.shared.network;

import java.math.BigInteger;

public interface Packet<T extends PacketListener> {

    void write(PacketBuf buf);
    void apply(T listener);
}