
package common;

import java.util.LinkedList;
import java.util.List;

//contains a list of all players and their positions to update the graphic
public class World implements Runnable {

	List<Manager> managerList;
	Manager manager;
	LinkedList<Object> world;

	public World(List<Manager> managerList, Manager manager) {
		this.managerList = managerList;
		this.manager = manager;
		world = new LinkedList<Object>();
	}

	public void addObjectToWorld(Object o) {
		System.out.println("added object to World");
		world.add(o);
	}

	public void removeObjecteFromWorld(Object o) {
		world.remove(o);
	}

	public LinkedList<Object> getWorld() {
		return world;
	}

	// Thread that creates NetMessages that will be send via all Managers to all
	// Clients
	@Override
	public void run() {
		System.out.println("world thread started");
		while (true) {
			for (Object o : world) {
				PosMessage msg = new PosMessage(o);
				// If Client -> Only one Manager
				
				if (managerList == null) {
					manager.write(msg);
				}

				// If Server -> List of Managers
				
				if (manager == null) {
					System.out.println(managerList.size());
					for (Manager m : managerList) {
						m.write(msg);
					}
				}
			}
			Thread.yield();
		}

	}
}
