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
    	//Server got Message from InputStream, gave it to Manager, Manager gave it to WorkingThread
        //Working Thread gave the Message to right Message Handler
		PosMessage p = (PosMessage)netMessage;
        Object o = p.getMsg();
        world.addObjectToWorld(o);
        System.out.println(o);
    }
}
