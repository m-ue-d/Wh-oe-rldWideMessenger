package spg.shared.chatting;

import javafx.scene.image.Image;

public class ChatImage extends ChatBase<Image> {

    public ChatImage(String content) {
        super(content);
    }

    @Override
    String encode(Image content) {
        return content.getUrl();
    }

    @Override
    Image decode(String content) { // TODO: image from url via GET request
        return new Image(content);
    }
}
