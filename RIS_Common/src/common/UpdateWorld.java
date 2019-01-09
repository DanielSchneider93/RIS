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
		GameObject temp = new GameObject(p);
		PosMessage msg = new PosMessage(temp);
		for (Manager m : managerList) {
			m.write(msg);
		}
	}

	//Server send Player
	public void sendClientThePlayer(GameObject p, Manager m) {
		GameObject temp = new GameObject(p);
		PosMessage msg = new PosMessage(temp);
		m.write(msg);
	}

	//Share Whole World
	public void shareWorldWithClients() {
		for (GameObject o : world) {
			for (Manager m : managerList) {
				GameObject temp = new GameObject(o);
				PosMessage msg = new PosMessage(temp);
				m.write(msg);
			}
		}
	}
}
