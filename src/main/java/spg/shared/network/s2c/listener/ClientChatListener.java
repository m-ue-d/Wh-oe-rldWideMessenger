package spg.shared.network.s2c.listener;

import spg.shared.network.PacketListener;
import spg.shared.network.s2c.FileSentDirectS2CPacket;
import spg.shared.network.s2c.ImageSentDirectS2CPacket;
import spg.shared.network.s2c.TextSentDirectS2CPacket;

public interface ClientChatListener extends PacketListener {
    void onTextSentDirect(TextSentDirectS2CPacket buf);

    void onImageSentDirect(ImageSentDirectS2CPacket buf);

    void onFileSentDirect(FileSentDirectS2CPacket buf);
}
