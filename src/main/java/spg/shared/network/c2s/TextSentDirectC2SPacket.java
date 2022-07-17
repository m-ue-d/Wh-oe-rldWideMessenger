package spg.shared.network.c2s;

import spg.shared.chatting.ChatText;
import spg.shared.network.c2s.listener.ServerChatListener;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;

/**
 * A packet sent by the client to the server to send a message to a specific user.
 */
public final class TextSentDirectC2SPacket implements Packet<ServerChatListener> {
    private final ChatText text;
    private final int targetUserId;

    public TextSentDirectC2SPacket(ChatText text, int targetUserId) {
        this.text = text;
        this.targetUserId = targetUserId;
    }

    public TextSentDirectC2SPacket(PacketBuf buf) {
        this.text = buf.readChatText();
        this.targetUserId = buf.readInt();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(text);
        buf.writeInt(targetUserId);
    }

    @Override
    public void apply(ServerChatListener listener) {
        listener.onTextSentDirect(this);
    }

    public ChatText getText() {
        return text;
    }

    public int getTargetUserId() {
        return targetUserId;
    }
}
