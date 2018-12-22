package common;

public class IDMessageHandler implements NetMessageInterface<IDMessage> {
	World world;
    @Override
    public MessageType getHandledMessageType() {
        return MessageType.ID;
    }

    @Override
    public void handle(NetMessage netMessage, World world, boolean isServer) {
    	this.world = world;
        IDMessage c = (IDMessage)netMessage;
        int id = c.getMsg();
        world.setPlayerID(id);
    }
}
