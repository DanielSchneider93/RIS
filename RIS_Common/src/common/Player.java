package common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

public class Player implements Serializable{
	
	transient BufferedImage player = null;
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

	public Player(int playerID) throws IOException {
		this.player = ImageIO.read(getClass().getResource("worm.png"));
		this.playerID = playerID;
	}

	public BufferedImage getPlayer() {
		return player;
	}

	public void setPlayer(BufferedImage player) {
		this.player = player;
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