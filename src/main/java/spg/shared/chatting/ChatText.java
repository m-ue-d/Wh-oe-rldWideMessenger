package spg.shared.chatting;

public class ChatText extends ChatBase<String> {

    public ChatText(String message) {
        super(message);
    }

    @Override
    String encode(String content) {
        return content;
    }

    @Override
    String decode(String content) {
        return content;
    }
}
