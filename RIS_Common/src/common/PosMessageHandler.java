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
		boolean isS = false;
		this.world = world;
		isS = isServer;
		Player play = new Player(0); // to get Class
		PosMessage p = (PosMessage) netMessage;
		System.out.println("posmessage" + p);
		Player playerFromMessage = (Player) p.getMsg();

		// world is empty
		if (world.getWorld().size() == 0) {
			// add player
			world.addObjectToWorld(playerFromMessage);

		} else {

			LinkedList<Object> worldcopy = world.getWorld();
			int doLoopThisMuchTimes = worldcopy.size();
			

			for (int z = 0; z < doLoopThisMuchTimes; z++) {
				System.out.println("worldsize "  + worldcopy.size());
				Object currentWorldObject = worldcopy.get(z);

				Class toCheckObjectClass = currentWorldObject.getClass();
				Class toCheckPlayerClass = play.getClass();

				// if Object in worldobject is player
				if (toCheckObjectClass == toCheckPlayerClass) {

					// Make Object Player
					Player toCheckTempPlayer = (Player) currentWorldObject;
					// Player tempPlay = (Player) worldobject;
					
					System.out.println("player from world id " +  toCheckTempPlayer.getPlayerID() + " player from message id " + playerFromMessage.getPlayerID());

					// if PlayerID is the same
					if (toCheckTempPlayer.getPlayerID() == playerFromMessage.getPlayerID()) {
						System.out.println("found player with id in world, dont need to update");
						// delete player in world and add the new one
						
						//world.removeObjecteFromWorld(currentWorldObject);
						//world.addObjectToWorld(playerFromMessage);

						//if (isS == true) {
						//	world.getUpdateWorld().shareWorldWithClients();
						//}
					}
					// if PlayerID is not the same
					else {
						world.addObjectToWorld(playerFromMessage);

						if (isS == true) {
							world.getUpdateWorld().shareWorldWithClients();
						}

					}
				}
			}
		}
	}
}
