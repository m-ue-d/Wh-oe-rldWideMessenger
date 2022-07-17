package spg.shared.chatting;

import java.io.File;

public class ChatFile extends ChatBase<File> {

    public ChatFile(String content) {
        super(content);
    }

    @Override
    String encode(File content) {
        return content.getPath();
    }

    @Override
    File decode(String content) { // TODO: image from url via GET request
        return new File(content);
    }
}
