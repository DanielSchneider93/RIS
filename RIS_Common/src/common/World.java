
package common;

import java.util.LinkedList;
import java.util.List;

public class World implements Runnable {

	List<Manager> managerList;
	Manager manager;
	LinkedList<Object> world;

	public World(List<Manager> managerList) {
		this.managerList = managerList;
		world = new LinkedList<Object>();
	}

	public void addObjectToWorld(Object o) {
		world.add(o);
	}

	public void removeObjecteFromWorld(Object o) {
		world.remove(o);
	}

	public LinkedList<Object> getWorld() {
		return world;
	}

	//TODO eigene Klasse für den Loop, send message only after change
	@Override
	public void run() {
		while (true) {
			LinkedList<Object> worldcopy = world;
			for (int x = 0; x < worldcopy.size(); x++) {
				Object o = worldcopy.get(x);
				PosMessage msg = new PosMessage(o);
				for (Manager m : managerList) {
					m.write(msg);
				}
			}
			Thread.yield();
		}
	}
}
