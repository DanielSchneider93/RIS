package common;

public class MapMessageHandler implements NetMessageInterface<IDMessage> {
	World world;
	Map map;
	
	public MapMessageHandler(World w) {
		this.world = w;
	}
	
    @Override
    public MessageType getHandledMessageType() {
        return MessageType.ID;
    }

    @Override
    public void handle(NetMessage netMessage, boolean isServer) {
        MapMessage m = (MapMessage)netMessage;
        map = (Map) m.getMsg();
        world.setSegmentList(map.getSegmentList());
    }
}
