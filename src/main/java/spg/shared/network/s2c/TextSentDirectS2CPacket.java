package spg.shared.network.s2c;

import spg.shared.chatting.ChatText;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientChatListener;

import java.time.LocalDateTime;

public final class TextSentDirectS2CPacket implements Packet<ClientChatListener> {
    private final ChatText text;
    private final int senderUserId;
    private final LocalDateTime timestamp;

    public TextSentDirectS2CPacket(ChatText text, int senderUserId, LocalDateTime timestamp) {
        this.text = text;
        this.senderUserId = senderUserId;
        this.timestamp = timestamp;
    }

    public TextSentDirectS2CPacket(PacketBuf buf) {
        this.text = buf.readChatText();
        this.senderUserId = buf.readInt();
        this.timestamp = buf.readTimestamp();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(text);
        buf.writeInt(senderUserId);
        buf.writeTimestamp(timestamp);
    }

    @Override
    public void apply(ClientChatListener listener) {
        listener.onTextSentDirect(this);
    }

    public ChatText getText() {
        return text;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
