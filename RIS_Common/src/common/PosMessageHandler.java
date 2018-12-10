package common;


public class PosMessageHandler implements NetMessageInterface<PosMessage> {
	World world;
    
	@Override
    public MessageType getHandledMessageType() {
        return MessageType.POS;
    }

    @Override
    public void handle(NetMessage netMessage, World world) {
    	this.world = world;
		PosMessage p = (PosMessage)netMessage;
        Object o = p.getMsg();
        world.addObjectToWorld(o);

    }
}
