package common;

import java.io.Serializable;

public class GameObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private int posx = 0;
	private int posy = 0;
	private int objectID = 0; //1-10 = player 20-30 = apple
	private int direction = 0; // 0 = left  - 1 = right
	private int collisonRadius = 0;

	
	public GameObject(int objectID, int posx, int posy, int collisonRadius ) {
		this.objectID = objectID;
		this.posx = posx;
		this.posy = posy;
		this.collisonRadius = collisonRadius;
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