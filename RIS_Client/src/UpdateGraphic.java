import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import common.Apple;
import common.Player;
import common.World;

public class UpdateGraphic extends JComponent {
	public World world;
	BufferedImage playerImage;
	BufferedImage playerImage_r;
	BufferedImage apple;

	int playerID;
	LinkedList<Player> players = new LinkedList<Player>();
	LinkedList<Apple> apples = new LinkedList<Apple>();

	public UpdateGraphic(World world) throws IOException {
		this.world = world;

		playerImage = ImageIO.read(getClass().getResource("worm.png"));
		playerImage_r = ImageIO.read(getClass().getResource("worm_r.png"));
		apple = ImageIO.read(getClass().getResource("apple.png"));

		this.playerID = world.getPlayerID();
	}

	public void paintComponent(Graphics g) {
		players = world.getPlayers();
		for (Player p : players) {
			int tempx = p.getPosx();
			int tempy = p.getPosy();
			if (p.getDirection() == 0) {
				g.drawImage(playerImage, tempx, tempy, null);
			} else {
				g.drawImage(playerImage_r, tempx, tempy, null);
			}
		}

		apples = world.getApples();
		for (Apple a : apples) {
			int tempx = a.getPosx();
			int tempy = a.getPosy();
			g.drawImage(apple, tempx, tempy, null);
		}
		
	}
}