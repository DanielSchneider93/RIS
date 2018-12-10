package common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Apple {
	BufferedImage apple = null;
	int posx = 0;
	int posy = 0;

	public Apple() throws IOException {
		apple = ImageIO.read(getClass().getResource("apple.png"));
	}

	public BufferedImage getApple() {
		return apple;
	}

	public void setApple(BufferedImage apple) {
		this.apple = apple;
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