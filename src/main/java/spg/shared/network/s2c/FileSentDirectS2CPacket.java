package spg.shared.network.s2c;

import spg.shared.chatting.ChatFile;
import spg.shared.chatting.ChatText;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientChatListener;

public final class FileSentDirectS2CPacket implements Packet<ClientChatListener> {
    private final ChatFile file;
    private final int senderUserId;

    public FileSentDirectS2CPacket(ChatFile text, int senderUserId) {
        this.file = text;
        this.senderUserId = senderUserId;
    }

    public FileSentDirectS2CPacket(PacketBuf buf) {
        this.file = buf.readChatFile();
        this.senderUserId = buf.readInt();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(file);
        buf.writeInt(senderUserId);
    }

    @Override
    public void apply(ClientChatListener listener) {
        listener.onFileSentDirect(this);
    }

    public ChatFile getFile() {
        return file;
    }

    public int getSenderUserId() {
        return senderUserId;
    }
}
