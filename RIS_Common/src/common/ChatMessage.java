package common;

public class ChatMessage implements NetMessage {
    String message;
    public ChatMessage(String msg) {
        this.message = msg;
    }

    @Override
    public String toString() {
        return this.message;
    }

}
