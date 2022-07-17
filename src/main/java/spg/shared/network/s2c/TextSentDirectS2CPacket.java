package spg.shared.network.s2c;

import spg.shared.chatting.ChatText;
import spg.shared.network.Packet;
import spg.shared.network.PacketBuf;
import spg.shared.network.s2c.listener.ClientChatListener;

public final class TextSentDirectS2CPacket implements Packet<ClientChatListener> {
    private final ChatText text;
    private final int senderUserId;

    public TextSentDirectS2CPacket(ChatText text, int senderUserId) {
        this.text = text;
        this.senderUserId = senderUserId;
    }

    public TextSentDirectS2CPacket(PacketBuf buf) {
        this.text = buf.readChatText();
        this.senderUserId = buf.readInt();
    }

    @Override
    public void write(PacketBuf buf) {
        buf.writeChatMessage(text);
        buf.writeInt(senderUserId);
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
}
