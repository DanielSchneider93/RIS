package common;

public class IDMessageHandler implements NetMessageInterface<IDMessage> {
	World world;
	
	public IDMessageHandler(World w) {
		this.world = w;
	}
	
	
    @Override
    public MessageType getHandledMessageType() {
        return MessageType.ID;
    }

    @Override
    public void handle(NetMessage netMessage, boolean isServer) {
        IDMessage c = (IDMessage)netMessage;
        int id = c.getMsg();
        world.setPlayerID(id);
    }
}
