package spg.shared.network.s2c;

import spg.shared.chatting.ChatImage;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientChatListener;

import java.time.LocalDateTime;

public final class ImageSentDirectS2CPacket implements Packet<ClientChatListener> {
    private final ChatImage image;
    private final int senderUserId;
    private final LocalDateTime timestamp;

    public ImageSentDirectS2CPacket(ChatImage text, int senderUserId, LocalDateTime timestamp) {
        this.image = text;
        this.senderUserId = senderUserId;
        this.timestamp = timestamp;
    }

    public ImageSentDirectS2CPacket(PacketBuf buf) {
        this.image = buf.readChatImage();
        this.senderUserId = buf.readInt();
        this.timestamp = buf.readTimestamp();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(image);
        buf.writeInt(senderUserId);
        buf.writeTimestamp(timestamp);
    }

    @Override
    public void apply(ClientChatListener listener) {
        listener.onImageSentDirect(this);
    }

    public ChatImage getImage() {
        return image;
    }

    public int getSenderUserId() {
        return senderUserId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
