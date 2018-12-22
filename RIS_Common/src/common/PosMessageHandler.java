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
		Player playerToHandle = (Player) p.getMsg();

		// world is empty
		if (world.getWorld().size() == 0) {
			// add player
			world.addObjectToWorld(playerToHandle);
		} else {

			LinkedList<Object> worldcopy = world.getWorld();

			for (int z = 0; z < worldcopy.size(); z++) {
				Object worldobject = worldcopy.get(z);

				Class toCheckObjectClass = worldobject.getClass();
				Class toCheckPlayerClass = play.getClass();

				// if Object in worldobject is player
				if (toCheckObjectClass == toCheckPlayerClass) {

					// Make Object Player
					Player toCheckTempPlayer = (Player) worldobject;
					Player tempPlay = (Player) worldobject;

					// if PlayerID is the same
					if (toCheckTempPlayer.getPlayerID() == playerToHandle.getPlayerID()) {
				
						// delete player in world and add the new one
						world.removeObjecteFromWorld(tempPlay);
						world.addObjectToWorld(playerToHandle);
						
						if(isServer == true) {
							world.getUpdateWorld().shareWorldWithClients();
						}
					}
					else {
						world.addObjectToWorld(playerToHandle);
						
						if(isServer == true) {
							world.getUpdateWorld().shareWorldWithClients();
						}
						
					}
				}
			}
		}
	}
}
