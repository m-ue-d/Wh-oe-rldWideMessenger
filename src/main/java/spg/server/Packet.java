package spg.server;

import java.io.Serializable;

public class Packet implements Serializable {
    public String identifier;
    public PacketByteBuffer content;

    public Packet(String identifier, PacketByteBuffer content) {
        this.identifier = identifier;
        this.content = content;
    }
}
