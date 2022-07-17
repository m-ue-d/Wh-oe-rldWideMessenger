package spg.shared.network.s2c;

import spg.shared.chatting.ChatImage;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientChatListener;

public final class ImageSentDirectS2CPacket implements Packet<ClientChatListener> {
    private final ChatImage image;
    private final int senderUserId;

    public ImageSentDirectS2CPacket(ChatImage text, int senderUserId) {
        this.image = text;
        this.senderUserId = senderUserId;
    }

    public ImageSentDirectS2CPacket(PacketBuf buf) {
        this.image = buf.readChatImage();
        this.senderUserId = buf.readInt();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(image);
        buf.writeInt(senderUserId);
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
}
