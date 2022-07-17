package spg.shared.network.c2s.listener;

import spg.shared.network.PacketListener;
import spg.shared.network.c2s.FileSentDirectC2SPacket;
import spg.shared.network.c2s.ImageSentDirectC2SPacket;
import spg.shared.network.c2s.TextSentDirectC2SPacket;

public interface ServerChatListener extends PacketListener {
    void onTextSentDirect(TextSentDirectC2SPacket buf);

    void onImageSentDirect(ImageSentDirectC2SPacket buf);

    void onFileSentDirect(FileSentDirectC2SPacket buf);
}
