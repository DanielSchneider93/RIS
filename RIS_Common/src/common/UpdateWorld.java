package common;

import java.util.LinkedList;
import java.util.List;

public class UpdateWorld {

	List<Manager> managerList;
	LinkedList<GameObject> world;

	public UpdateWorld(LinkedList<GameObject> world, List<Manager> managerList) {
		this.world = world;
		this.managerList = managerList;
	}

	//Send Solo Message
	public void sendPlayerMessage(GameObject p) {
		PosMessage msg = new PosMessage(p);
		for (Manager m : managerList) {
			m.write(msg);
		}
	}

	//Server send Player
	public void sendClientThePlayer(GameObject p, Manager connectionManager) {
		PosMessage msg = new PosMessage(p);
		connectionManager.write(msg);
	}

	//Share Whole World
	public void shareWorldWithClients() {
		for (GameObject o : world) {
			PosMessage msg = new PosMessage(o);
			for (Manager m : managerList) {
				m.write(msg);
			}
		}
	}
}
