package common;

public interface NetMessageInterface<T extends NetMessage>{
    public MessageType getHandledMessageType();
    public void handle(NetMessage netMessage, boolean isServer);
}
