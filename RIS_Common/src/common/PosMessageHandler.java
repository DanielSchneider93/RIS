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
		boolean isInWorld = false;
		boolean isS = false;
		this.world = world;
		isS = isServer;
		Player play = new Player(0); // to get Class
		PosMessage p = (PosMessage) netMessage;
		Player playerFromMessage = (Player) p.getMsg();

		if (world.getWorld().size() == 0) {
			world.addObjectToWorld(playerFromMessage);

		} else {

			LinkedList<Object> worldcopy = world.getWorld();
			int doLoopThisMuchTimes = worldcopy.size();

			for (int z = 0; z < doLoopThisMuchTimes; z++) {
				Object currentWorldObject = worldcopy.get(z);

				Class toCheckObjectClass = currentWorldObject.getClass();
				Class toCheckPlayerClass = play.getClass();

				if (toCheckObjectClass == toCheckPlayerClass) {

					Player toCheckTempPlayer = (Player) currentWorldObject;
					if (toCheckTempPlayer.getPlayerID() == playerFromMessage.getPlayerID()) {
						isInWorld = true;
						world.removeObjecteFromWorld(toCheckTempPlayer);
						world.addObjectToWorld(playerFromMessage);
					}
				}
			}

			if (!isInWorld) {
				world.addObjectToWorld(playerFromMessage);

			}
			else {
				
			}
			
			if (isS == true) {
				world.getUpdateWorld().shareWorldWithClients();
			}
		}
	}
}
