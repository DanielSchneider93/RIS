
package common;

import java.util.LinkedList;
import java.util.List;

public class World {

	List<Manager> managerList;
	LinkedList<GameObject> world;
	UpdateWorld updateWorld;
	int playerID;
	Class<?> playerclass;
	Class<?> appleclass;

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public World(List<Manager> managerList) {
		this.managerList = managerList;
		world = new LinkedList<GameObject>();
		updateWorld = new UpdateWorld(world, managerList);
	}

	public UpdateWorld getUpdateWorld() {
		return updateWorld;
	}

	public void addObjectToWorld(GameObject o) {
		world.add(o);
	}

	public void removeObjectFromWorld(GameObject o) {
		world.remove(o);
	}

	public LinkedList<GameObject> getWorld() {
		return world;
	}

	public void triggerPosChange(GameObject object) {
		GameObject p = object;
		updateWorld.sendPlayerMessage(p);
	}

	public LinkedList<GameObject> getApples() {
		LinkedList<GameObject> copyList = world;
		LinkedList<GameObject> result = new LinkedList<GameObject>();

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
			if (o.getID() >= 20 && o.getID() <= 30) {
				result.add(o);
			}
		}
		return result;
	}

	public LinkedList<GameObject> getPlayers() {
		LinkedList<GameObject> copyList = world;
		LinkedList<GameObject> result = new LinkedList<GameObject>();

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
			if (o.getID() >= 1 && o.getID() <= 10) {
				result.add(o);
			}
		}
		return result;
	}

	public GameObject findPlayer(int ObjectID) {
		LinkedList<GameObject> copyList = world;
		GameObject player = null;

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
				if (o.getID() == ObjectID) {
					player = o;
				}
			}
		return player;
	}
}
