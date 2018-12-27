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
		//System.out.println("world size start handle " + world.getWorld().size());
		boolean isInWorld = false;
		boolean isS = false;
		this.world = world;
		isS = isServer;

		LinkedList<GameObject> worldcopy = world.getWorld();

		PosMessage message = (PosMessage) netMessage;

		GameObject oFromMessage = (GameObject) message.getMsg();
		System.out.println("id " + oFromMessage.getID() + " delete: "  +   oFromMessage.isDeleteThis() + " eatable: " + oFromMessage.isEatable());
		

		
		if(oFromMessage.isDeleteThis() == true) {
			world.getWorld().remove(oFromMessage);
			return;
		}
		
		isInWorld = checkIfObjectExistsInWorld(worldcopy, oFromMessage, isInWorld, isS);

		if (!isInWorld) {
			//System.out.println("is not in world -> add");
			world.addObjectToWorld(oFromMessage);
		}

		if (isS == true) {
			//System.out.println("is server and share with clients");
			world.getUpdateWorld().shareWorldWithClients();
		}
		System.out.println("world size end handle " + world.getWorld().size());
	}

	public boolean checkIfObjectExistsInWorld(LinkedList<GameObject> worldcopy, GameObject objectFromMessage, boolean isInWorld, boolean isS) {
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
					//System.out.println("collision? = " + collision);
					if (!collision) {
						world.removeObjectFromWorld(currentWorldObject);
					}
					else {
						//System.out.println("get the collided object");
						GameObject collidedWith = collisionDetection.getCollisionWithThisObject();
						if(collidedWith.isEatable()) {
							
							GameObject toSendDeleteObject = collidedWith;
							toSendDeleteObject.setDeleteThis(true);
							toSendDeleteObject.setEatable(false);
							System.out.println(" deleted set? " + toSendDeleteObject.isDeleteThis());
							world.getUpdateWorld().sendPlayerMessage(toSendDeleteObject);
							System.out.println(" after setting the variables of object, id " + collidedWith.getID() + " delete: "  +   collidedWith.isDeleteThis() + " eatable: " + collidedWith.isEatable());
							
							world.getWorld().remove(collidedWith);
							System.out.println("delete Message send & delete from server world");
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
