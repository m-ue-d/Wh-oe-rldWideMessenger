package spg.shared.network.c2s;

import spg.shared.chatting.ChatFile;
import spg.shared.network.c2s.listener.ServerChatListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to send a file to a specific user.
 */
public final class FileSentDirectC2SPacket implements Packet<ServerChatListener> {
    private final ChatFile file;
    private final int targetUserId;

    public FileSentDirectC2SPacket(ChatFile file, int targetUserId) {
        this.file = file;
        this.targetUserId = targetUserId;
    }

    public FileSentDirectC2SPacket(PacketBuf buf) {
        this.file = buf.readChatFile();
        this.targetUserId = buf.readInt();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(file);
        buf.writeInt(targetUserId);
    }

    @Override
    public void apply(ServerChatListener listener) {
        listener.onFileSentDirect(this);
    }

    public ChatFile getFile() {
        return file;
    }

    public int getTargetUserId() {
        return targetUserId;
    }
}
