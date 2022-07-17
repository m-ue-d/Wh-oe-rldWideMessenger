package spg.shared.network.c2s;

import spg.shared.chatting.ChatImage;
import spg.shared.network.c2s.listener.ServerChatListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to send a message to a specific user.
 */
public final class ImageSentDirectC2SPacket implements Packet<ServerChatListener> {
    private final ChatImage image;
    private final int targetUserId;

    public ImageSentDirectC2SPacket(ChatImage image, int targetUserId) {
        this.image = image;
        this.targetUserId = targetUserId;
    }

    public ImageSentDirectC2SPacket(PacketBuf buf) {
        this.image = buf.readChatImage();
        this.targetUserId = buf.readInt();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(image);
        buf.writeInt(targetUserId);
    }

    @Override
    public void apply(ServerChatListener listener) {
        listener.onImageSentDirect(this);
    }

    public ChatImage getImage() {
        return image;
    }

    public int getTargetUserId() {
        return targetUserId;
    }
}
