package common;

import java.util.LinkedList;

public class PosMessageHandler implements NetMessageInterface<PosMessage> {
	World world;
	LinkedList<GameObject> wList;

	public PosMessageHandler(World w) {
		this.world = w;
	}

	@Override
	public MessageType getHandledMessageType() {
		return MessageType.POS;
	}

	@Override
	public void handle(NetMessage netMessage, boolean isS) {
		wList = world.getWorld();
		boolean isServer = isS;
		boolean end = false;
		boolean collision = false;
		boolean mapCollision = false;
		GameObject collidedWith = null;
		PosMessage message = (PosMessage) netMessage;
		GameObject oFromMessage = (GameObject) message.getMsg();
		
		//System.out.println("oFromMessage " +  oFromMessage.getPosx() + " world " + world.findPlayer(world.getPlayerID()).getPosx());

		// -------------------------------------------Server-------------------------------------------------

		if (isServer) {

			if (oFromMessage.getID() == 99) {
				world.setCache(oFromMessage.getCache());
				return;
			}

			boolean isInWorld = false;

			for (int z = 0; z < wList.size(); z++) {
				GameObject currentGameObject = wList.get(z);
				CollisionDetection collisionDetection = new CollisionDetection();

				if (currentGameObject.getID() == oFromMessage.getID()) {
					isInWorld = true;

					collision = collisionDetection.detect(currentGameObject, oFromMessage, wList, world.getCache());

					if (!collision) {
						world.removeObjectFromWorldWithID(currentGameObject.getID());
						world.addObjectToWorld(oFromMessage);
					} else {

						collidedWith = collisionDetection.getCollisionWithThisObject();

						if (collidedWith == null) {
							mapCollision = true;
							System.out.println("collided with wall");
						}

						if (!mapCollision) {

							if (collidedWith.isEatable()) {
								int counter = 0;
								for (GameObject go : world.getWorld()) {

									if (go.getID() == collidedWith.getID()) {
										world.getWorld().get(counter).setDelete(true);
									}
									counter++;
								}
							}
						}
					}
				}
			}

			if (!isInWorld) {
				world.addObjectToWorld(oFromMessage);
			}

			world.getUpdateWorld().shareWorldWithClients();

			if (collision && !mapCollision) {
				world.getWorld().remove(collidedWith);
			}
		}

		// --------------------------------------------Client-------------------------------------------------

		if (!isServer) {
			boolean isInWorld = false;

			// Check if Object should be deleted
			for (int z = 0; z < wList.size(); z++) {
				GameObject gameO = wList.get(z);
				if (oFromMessage.getID() == gameO.getID())
					if (oFromMessage.isDelete() == true) {
						System.out.println("remove object from world " + gameO.getID());
						world.removeObjectFromWorldWithID(gameO.getID());
						end = true;
						break;
					}
			}

			// If Object is deleted -> stop
			if (!end) {
				for (int z = 0; z < wList.size(); z++) {
					GameObject currentWorldObject = wList.get(z);

					if (currentWorldObject.getID() == oFromMessage.getID()) {
						isInWorld = true;
						world.removeObjectFromWorldWithID(currentWorldObject.getID());
						world.addObjectToWorld(oFromMessage);
						//break;
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
