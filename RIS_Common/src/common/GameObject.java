package common;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObject implements Serializable {

	// TODO: make enum out of ID chaos

	private static final long serialVersionUID = 1L;
	private int posx = 0;
	private int posy = 0;
	private int objectID = 0; // 1-10 = player 20-30 = apple, 1000++ = bullet
	private int direction = 0; // 0 = left - 1 = right
	private int collisonRadius = 0;
	private boolean eatable = false;
	private boolean delete = false;
	private ArrayList<WorldSegment> cache = null;
	private int health = 0;

	public ArrayList<WorldSegment> getCache() {
		return cache;
	}

	public void setCache(ArrayList<WorldSegment> cache) {
		this.cache = cache;
	}

	// new Player or Apple
	public GameObject(int objectID, int posx, int posy, int collisonRadius, boolean eatable, int health) {
		this.objectID = objectID;
		this.posx = posx;
		this.posy = posy;
		this.collisonRadius = collisonRadius;
		this.eatable = eatable;
		this.health = health;
	}

	// for Messaging
	public GameObject(GameObject o) {
		this.posx = o.getPosx();
		this.posy = o.getPosy();
		this.objectID = o.getID(); // 1-10 = player 20-30 = apple
		this.direction = o.getDirection(); // 0 = left - 1 = right
		this.collisonRadius = o.getCollisonRadius();
		this.eatable = o.isEatable();
		this.delete = o.isDelete();
		this.cache = o.getCache();
		this.health = o.getHealth();
	}

	// bullets
	public GameObject(int ID, int x, int y) {
		this.objectID = ID;
		this.posx = x;
		this.posy = y;
	}
	
	

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	public boolean isEatable() {
		return eatable;
	}

	public void setEatable(boolean eatable) {
		this.eatable = eatable;
	}

	public int getCollisonRadius() {
		return collisonRadius;
	}

	public void setCollisonRadius(int collisonRadius) {
		this.collisonRadius = collisonRadius;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getID() {
		return objectID;
	}

	public void setID(int objectID) {
		this.objectID = objectID;
	}

	public int getPosx() {
		return posx;
	}

	public void setPosx(int posx) {
		this.posx = posx;
	}

	public int getPosy() {
		return posy;
	}

	public void setPosy(int posy) {
		this.posy = posy;
	}
}