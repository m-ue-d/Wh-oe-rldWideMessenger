package spg.shared.chatting;

public abstract class ChatBase<T> {
    private final T content;

    public ChatBase(String content) {
        this.content = decode(content);
    }

    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return encode(content);
    }

    abstract String encode(T content);
    abstract T decode(String content);
}
