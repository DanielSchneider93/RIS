package common;

import java.util.LinkedList;
import java.util.List;

public class UpdateWorld {

	private List<Manager> managerList;
	private LinkedList<GameObject> world;

	public UpdateWorld(LinkedList<GameObject> world, List<Manager> managerList) {
		this.world = world;
		this.managerList = managerList;
	}

	// Send Solo Message
	public void sendPlayerMessage(GameObject p) {
		for (Manager m : managerList) {
			GameObject temp = new GameObject(p);
			PosMessage msg = new PosMessage(temp);
			m.write(msg);
		}
	}

	// Server sends Client the Player
	public void sendClientThePlayer(GameObject p, Manager m) {
		GameObject temp = new GameObject(p);
		PosMessage msg = new PosMessage(temp);
		m.write(msg);
	}

	// Share whole World
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
