package common;

public class ChatMessageHandler implements NetMessageInterface<ChatMessage> {
	World world;
    @Override
    public MessageType getHandledMessageType() {
        return MessageType.CHAT;
    }

    // Einfach die Daten verarbeiten.
    @Override
    public void handle(NetMessage netMessage, World world) {
    	this.world = world;
        ChatMessage c = (ChatMessage)netMessage;
        System.out.println(c.toString());
    }
}
