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
        //TODO Check if Object in World if yes update if no add
        world.addObjectToWorld(o);
    }
}
