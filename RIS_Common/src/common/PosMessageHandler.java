package common;

import java.util.LinkedList;

public class PosMessageHandler implements NetMessageInterface<PosMessage> {
	World world;
	CollisionDetection cd = new CollisionDetection();

	@Override
	public MessageType getHandledMessageType() {
		return MessageType.POS;
	}

	@Override
	public void handle(NetMessage netMessage, World world, boolean isServer) {
		boolean isInWorld = false;
		boolean isS = false;
		this.world = world;
		isS = isServer;

		LinkedList<GameObject> worldcopy = world.getWorld();

		PosMessage message = (PosMessage) netMessage;

		GameObject objectFromMessage = (GameObject) message.getMsg();

		isInWorld = checkIfObjectExistsInWorld(worldcopy, objectFromMessage, isInWorld);

		if (!isInWorld) {
			world.addObjectToWorld(objectFromMessage);
		}

		if (isS == true) {
			world.getUpdateWorld().shareWorldWithClients();
		}
	}

	public boolean checkIfObjectExistsInWorld(LinkedList<GameObject> worldcopy, GameObject objectFromMessage, boolean isInWorld) {

		int count = worldcopy.size();

		for (int z = 0; z < count; z++) {
			GameObject currentWorldObject = worldcopy.get(z);

			if (currentWorldObject.getID() == objectFromMessage.getID()) {
				isInWorld = true;
				cd.detect(currentWorldObject, objectFromMessage, worldcopy);
				//If Collision decide what to do
				world.removeObjecteFromWorld(currentWorldObject);
				world.addObjectToWorld(objectFromMessage);
				
			}
		}
		return isInWorld;
	}
}
