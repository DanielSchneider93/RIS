import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import common.GameObject;
import common.World;

public class UpdateGraphic extends JComponent {
	private static final long serialVersionUID = 1L;
	public World world;
	BufferedImage playerImage;
	BufferedImage playerImage_r;
	BufferedImage apple;

	int playerID;
	LinkedList<GameObject> players = new LinkedList<GameObject>();
	LinkedList<GameObject> apples = new LinkedList<GameObject>();

	public UpdateGraphic(World world) throws IOException {
		this.world = world;

		playerImage = ImageIO.read(getClass().getResource("worm.png"));
		playerImage_r = ImageIO.read(getClass().getResource("worm_r.png"));
		apple = ImageIO.read(getClass().getResource("apple.png"));

		this.playerID = world.getPlayerID();
	}

	public void paintComponent(Graphics g) {
		players = world.getPlayers();
		for (GameObject p : players) {
			int tempx = p.getPosx();
			int tempy = p.getPosy();
			if (p.getDirection() == 0) {
				g.drawImage(playerImage, tempx, tempy, null);
			} else {
				g.drawImage(playerImage_r, tempx, tempy, null);
			}
		}

		apples = world.getApples();
		for (GameObject tempApple : apples) {
			int tempx = tempApple.getPosx();
			int tempy = tempApple.getPosy();
			g.drawImage(apple, tempx, tempy, null);
		}		
	}
}