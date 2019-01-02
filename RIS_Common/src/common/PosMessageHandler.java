package common;

import java.util.LinkedList;

public class PosMessageHandler implements NetMessageInterface<PosMessage> {
	World world;
	LinkedList<GameObject> wList;

	public PosMessageHandler(World w) {
		this.world = w;
		this.wList = world.getWorld();
	}

	@Override
	public MessageType getHandledMessageType() {
		return MessageType.POS;
	}

	@Override
	public void handle(NetMessage netMessage, boolean isS) {

		boolean isServer = isS;
		boolean end = false;
		PosMessage message = (PosMessage) netMessage;
		GameObject oFromMessage = (GameObject) message.getMsg();

		// -------------------------------------------Server-------------------------------------------------

		if (isServer) {
			boolean isInWorld = false;

			int count = wList.size();

			for (int z = 0; z < count; z++) {
				GameObject currentGameObject = wList.get(z);
				CollisionDetection collisionDetection = new CollisionDetection();

				if (currentGameObject.getID() == oFromMessage.getID()) {
					isInWorld = true;

					boolean collision = collisionDetection.detect(currentGameObject, oFromMessage, wList);

					if (!collision) {
						world.removeObjectFromWorldWithID(currentGameObject.getID());
						world.addObjectToWorld(oFromMessage);
					} else {

						GameObject collidedWith = collisionDetection.getCollisionWithThisObject();

						if (collidedWith.isEatable()) {
							int counter = 0;
							for (GameObject go : world.getWorld()) {
								// counter++;
								int element = 1;
								if (go.getID() == collidedWith.getID()) {
									System.out.println("collided with " + collidedWith.getID());
									world.getWorld().get(element).setDelete(true);
								}
								counter++;
							}
							world.getWorld().remove(collidedWith);
						}
					}
				}
			}

			if (!isInWorld) {
				world.addObjectToWorld(oFromMessage);
			}
			world.getUpdateWorld().shareWorldWithClients();
		}

		// --------------------------------------------Client-------------------------------------------------

		if (!isServer) {
			boolean isInWorld = false;

			// Check if Objects should be deleted
			int times = wList.size();
			for (int z = 0; z < times; z++) {
				GameObject gameO = wList.get(z);
				if (oFromMessage.getID() == gameO.getID())
					if (oFromMessage.isDelete() == true) {
						System.out.println("remove object from world " + gameO.getID());
						world.removeObjectFromWorldWithID(gameO.getID());
						end = true;
					}
			}

			// If Object is deleted -> stop
			if (!end) {
				int count = wList.size();
				for (int z = 0; z < count; z++) {
					GameObject currentWorldObject = wList.get(z);

					if (currentWorldObject.getID() == oFromMessage.getID()) {
						isInWorld = true;
						world.removeObjectFromWorldWithID(currentWorldObject.getID());
						world.addObjectToWorld(oFromMessage);
					}
				}

				if (!isInWorld) {
					world.addObjectToWorld(oFromMessage);
				}
			}

		}

		System.out.println("world size end handle " + wList.size());
	}
}
