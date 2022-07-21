package spg.server.network;

import spg.shared.chatting.ChatText;
import spg.shared.network.c2s.listener.ServerChatListener;
import spg.shared.network.ClientConnection;
import spg.shared.network.c2s.FileSentDirectC2SPacket;
import spg.shared.network.c2s.ImageSentDirectC2SPacket;
import spg.shared.network.c2s.TextSentDirectC2SPacket;
import spg.shared.network.s2c.TextSentDirectS2CPacket;

import java.time.LocalDateTime;

public class ServerChatHandler implements ServerChatListener {

    private final ClientConnection connection;
    private final int myUserId = 12345; // TODO: remove this

    public ServerChatHandler(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void onTextSentDirect(TextSentDirectC2SPacket buf) {
        int targetUserId = buf.getTargetUserId();
        ChatText message = buf.getText();
        ServerNetwork.INSTANCE.getConnection(targetUserId)
            .send(new TextSentDirectS2CPacket(message, myUserId, LocalDateTime.now()));

        connection.send(new TextSentDirectS2CPacket(message, myUserId, LocalDateTime.now()));
    }

    @Override
    public void onImageSentDirect(ImageSentDirectC2SPacket buf) {

    }

    @Override
    public void onFileSentDirect(FileSentDirectC2SPacket buf) {

    }
}
