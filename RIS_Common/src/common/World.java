
package common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class World {

	List<Manager> managerList;
	LinkedList<GameObject> world;
	ArrayList<WorldSegment> segmentList;
	ArrayList<WorldSegment> cache;
	UpdateWorld updateWorld;
	int playerID;
	Class<?> playerclass;
	Class<?> appleclass;
	
	public World(List<Manager> managerList) {
		this.managerList = managerList;
		cache = new ArrayList<WorldSegment>();
		world = new LinkedList<GameObject>();
		updateWorld = new UpdateWorld(world, managerList);
	}
	
	
	public void triggerPosChange(GameObject object) {
		GameObject p = object;
		updateWorld.sendPlayerMessage(p);
	}

	public void removeObjectFromWorldWithID(Integer id) {
		LinkedList<GameObject> copyList = new LinkedList<GameObject>(world);

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
			if (o.getID() == id) {
				world.remove(o);
			}
		}
	}

	public LinkedList<GameObject> getApples() {
		LinkedList<GameObject> copyList = new LinkedList<GameObject>(world);
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
		LinkedList<GameObject> copyList = new LinkedList<GameObject>(world);
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
		LinkedList<GameObject> copyList = new LinkedList<GameObject>(world);
		GameObject player = null;

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
			if (o.getID() == ObjectID) {
				player = o;
			}
		}
		return player;
	}

	public int getPlayerPosX() {
		int playerPosX = 0;
		LinkedList<GameObject> copyList = world;

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
			if (o.getID() == playerID) {
				playerPosX = o.getPosx();
			}
		}
		return playerPosX;
	}

	public int getPlayerPosY() {
		int playerPosY = 0;
		LinkedList<GameObject> copyList = world;

		for (int z = 0; z < copyList.size(); z++) {
			GameObject o = copyList.get(z);
			if (o.getID() == playerID) {
				playerPosY = o.getPosy();
			}
		}
		return playerPosY;
	}
	
	public ArrayList<WorldSegment> getCache() {
		return cache;
	}

	public void setCache(ArrayList<WorldSegment> cache) {
		this.cache = cache;
	}

	public ArrayList<WorldSegment> getSegmentList() {
		return segmentList;
	}

	public void setSegmentList(ArrayList<WorldSegment> segmentList) {
		this.segmentList = segmentList;
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
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


}
