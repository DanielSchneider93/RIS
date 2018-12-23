
package common;

import java.util.LinkedList;
import java.util.List;

public class World {

	List<Manager> managerList;
	LinkedList<Object> world;
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
		world = new LinkedList<Object>();
		updateWorld = new UpdateWorld(world, managerList);
		Player player = new Player(0);
		playerclass = player.getClass();
		Apple apple = new Apple(0,0);
		appleclass = apple.getClass();
	}

	public UpdateWorld getUpdateWorld() {
		return updateWorld;
	}

	public void addObjectToWorld(Object o) {
		// System.out.println("add Object to World " + o);
		world.add(o);
	}

	public void removeObjecteFromWorld(Object o) {
		// System.out.println("remove Object from World " + o);
		world.remove(o);
	}

	public LinkedList<Object> getWorld() {
		return world;
	}

	public void triggerPosChange(Player player) {
		Player p = player;
		updateWorld.sendPlayerMessage(p);
	}

	public LinkedList<Apple> getApples() {
		LinkedList<Object> copyList = world;
		LinkedList<Apple> result = new LinkedList<Apple>();

		for (int z = 0; z < copyList.size(); z++) {
			Object o = copyList.get(z);
			if (o.getClass() == appleclass) {
				Apple p = (Apple) o;
				result.add(p);
			}
		}
		return result;
	}

	public LinkedList<Player> getPlayers() {
		LinkedList<Object> copyList = world;
		LinkedList<Player> result = new LinkedList<Player>();

		for (int z = 0; z < copyList.size(); z++) {
			Object o = copyList.get(z);
			Class<?> c = o.getClass();
			if (c == playerclass) {
				Player p = (Player) o;
				result.add(p);
			}
		}
		return result;
	}

	public Player findPlayer(int playerID) {
		LinkedList<Object> copyList = world;
		Player player = null;

		for (int z = 0; z < copyList.size(); z++) {
			Object o = copyList.get(z);
			Class<?> c = o.getClass();
			if (c == playerclass) {
				Player p = (Player) o;
				if (p.getPlayerID() == playerID) {
					player = p;
				}
			}
		}
		return player;
	}
}
