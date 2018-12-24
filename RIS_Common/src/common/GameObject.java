package common;

import java.io.File;
import java.io.Serializable;

public class GameObject implements Serializable{
	
	File file;
	int posx = 200;
	int posy = 200;
	int objectID = 0; //1-10 = player 20-30 = apple
	int direction = 0;

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

	public GameObject(int objectID, int posx, int posy) {
		this.objectID = objectID;
		this.posx = posx;
		this.posy = posy;
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