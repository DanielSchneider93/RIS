package common;

public interface NetMessageInterface<T extends NetMessage>{
    public MessageType getHandledMessageType();
    public void handle(NetMessage netMessage, World world, boolean isServer);
}
