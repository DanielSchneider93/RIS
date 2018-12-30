package common;

import java.util.LinkedList;

public class PosMessageHandler implements NetMessageInterface<PosMessage> {
	World world;

	@Override
	public MessageType getHandledMessageType() {
		return MessageType.POS;
	}

	@Override
	public void handle(NetMessage netMessage, World world, boolean isServer) {
		// System.out.println("world size start handle " + world.getWorld().size());
		boolean isInWorld = false;
		boolean isS = false;
		this.world = world;
		isS = isServer;
		boolean end = false;

		LinkedList<GameObject> worldcopy = world.getWorld();

		PosMessage message = (PosMessage) netMessage;

		GameObject oFromMessage = (GameObject) message.getMsg();

		for (GameObject o : world.getWorld()) {
			System.out.println("o. " + o.getID());
			if (oFromMessage.getID() == o.getID())
				if (oFromMessage.isDelete() == true & !isS) {
					System.out.println("remove object from world " + o.getID());
					world.getWorld().remove(o);
					end = true;
				}
		}

		if (!end) {
			isInWorld = checkIfObjectExistsInWorld(worldcopy, oFromMessage, isInWorld, isS);

			if (!isInWorld) {
				// System.out.println("is not in world -> add");
				world.addObjectToWorld(oFromMessage);
			}

			if (isS == true) {
				// System.out.println("is server and share with clients");
				world.getUpdateWorld().shareWorldWithClients();
			}
			System.out.println("world size end handle " + world.getWorld().size());
		}
	}

	public boolean checkIfObjectExistsInWorld(LinkedList<GameObject> worldcopy, GameObject objectFromMessage,
			boolean isInWorld, boolean isS) {
		GameObject o = objectFromMessage;
		int count = worldcopy.size();
		boolean isServ = isS;
		LinkedList<GameObject> worldCopyTemp = worldcopy;

		for (int z = 0; z < count; z++) {
			GameObject currentWorldObject = worldCopyTemp.get(z);
			CollisionDetection collisionDetection = new CollisionDetection();

			if (currentWorldObject.getID() == o.getID()) {
				isInWorld = true;

				if (isServ) {
					boolean collision = collisionDetection.detect(currentWorldObject, objectFromMessage, worldCopyTemp);
					// System.out.println("collision? = " + collision);
					if (!collision) {
						world.removeObjectFromWorld(currentWorldObject);
					} else {
						// System.out.println("get the collided object");
						GameObject collidedWith = collisionDetection.getCollisionWithThisObject();
						if (collidedWith.isEatable()) {
							int counter = 0;
							for (GameObject go : world.getWorld()) {
								counter++;
								//counter wrong
								if (go.getID() == collidedWith.getID()) {
									System.out.println("collided with " + collidedWith.getID());
									world.getWorld().get(counter).setDelete(true);
								}
							}
							world.getWorld().remove(collidedWith);
						}
					}

				} else {
					world.removeObjectFromWorld(currentWorldObject);
					world.addObjectToWorld(objectFromMessage);
				}
			}
		}
		return isInWorld;
	}
}
