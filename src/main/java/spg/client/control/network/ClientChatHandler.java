package spg.client.control.network;

import spg.shared.network.ClientConnection;
import spg.shared.network.s2c.FileSentDirectS2CPacket;
import spg.shared.network.s2c.ImageSentDirectS2CPacket;
import spg.shared.network.s2c.TextSentDirectS2CPacket;
import spg.shared.network.s2c.listener.ClientChatListener;

public class ClientChatHandler implements ClientChatListener {

    private final ClientConnection connection;

    public ClientChatHandler(ClientConnection connection) {
        this.connection = connection;
    }

    @Override
    public void onTextSentDirect(TextSentDirectS2CPacket buf) {
        System.out.println("Received text message: " + buf.getText() + " from user " + buf.getSenderUserId());
    }

    @Override
    public void onImageSentDirect(ImageSentDirectS2CPacket buf) {
        System.out.println("Received image from user " + buf.getSenderUserId());
    }

    @Override
    public void onFileSentDirect(FileSentDirectS2CPacket buf) {
        System.out.println("Received file from user " + buf.getSenderUserId());
    }
}