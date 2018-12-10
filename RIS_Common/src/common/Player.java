package common;

import java.io.File;
import java.io.Serializable;

public class Player implements Serializable{
	
	File file;
	int posx = 200;
	int posy = 200;
	int playerID = 0;

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Player(int playerID) {
		this.playerID = playerID;
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