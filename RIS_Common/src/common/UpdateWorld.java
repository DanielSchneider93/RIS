package common;

import java.util.LinkedList;
import java.util.List;

public class UpdateWorld {

	List<Manager> managerList;
	LinkedList<Object> world;

	public UpdateWorld(LinkedList<Object> world, List<Manager> managerList) {
		this.world = world;
		this.managerList = managerList;
	}

	public void sendUpdatedWorld() {
		LinkedList<Object> worldcopy = world;
		for (int x = 0; x < worldcopy.size(); x++) {
			Object o = worldcopy.get(x);
			PosMessage msg = new PosMessage(o);
			for (Manager m : managerList) {
				m.write(msg);
			}
		}
	}
}
