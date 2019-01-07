import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import common.GameObject;
import common.World;
import common.WorldSegment;

public class UpdateGraphic extends JComponent {
	private static final long serialVersionUID = 1L;
	World world;
	private BufferedImage playerImage;
	private BufferedImage playerImage_r;
	private BufferedImage apple;
	private BufferedImage grass;
	private BufferedImage wall;
	int windowOffsetX = 0;
	int windowOffsetY = 0;
	private int staticPlayerPos = 400;

	int playerID;
	LinkedList<GameObject> players;
	LinkedList<GameObject> apples;
	ArrayList<WorldSegment> cache;

	public UpdateGraphic(World world) throws IOException {
		this.world = world;
		playerImage = ImageIO.read(getClass().getResource("worm.png"));
		playerImage_r = ImageIO.read(getClass().getResource("worm_r.png"));
		apple = ImageIO.read(getClass().getResource("apple.png"));
		grass = ImageIO.read(getClass().getResource("gras.png"));
		wall = ImageIO.read(getClass().getResource("wall.png"));
		this.playerID = world.getPlayerID();
		cache = world.getCache();
	}

	public void paintComponent(Graphics g) {
		players = new LinkedList<GameObject>(world.getPlayers());
		apples = new LinkedList<GameObject>(world.getApples());
		cache = new ArrayList<WorldSegment>(world.getCache());
		
		if (cache != null) {
			for (int m = 0; m < cache.size(); m++) {

				WorldSegment s = cache.get(m);

				if (s.getList() != null) {
					ArrayList<Integer> list = s.getList();
					int posx = s.getX();
					int posy = s.getY();
					int counter = 0;
					for (Integer i : list) {
						counter++;
						if (i == 0) {
							g.drawImage(grass, posx + windowOffsetX, posy + windowOffsetY, null);
						}
						if (i == 1) {
							g.drawImage(wall, posx + windowOffsetX, posy + windowOffsetY, null);
						}
						posx += 100;
						if (counter == 10) {
							posy += 100;
							posx = s.getX();
							counter = 0;
						}
					}
				}
			}
		}

		for (GameObject player : players) {
			if (playerID == player.getID()) {
				if (player.getDirection() == 0) { // Look left
					g.drawImage(playerImage, staticPlayerPos, staticPlayerPos, null);
				} else { // Look Right
					g.drawImage(playerImage_r, staticPlayerPos, staticPlayerPos, null);
				}
			} else {
				if (player.getDirection() == 0) { // Look left
					g.drawImage(playerImage, player.getPosx() + windowOffsetX, player.getPosy() + windowOffsetY, null);
				} else { // Look Right
					g.drawImage(playerImage_r, player.getPosx() + windowOffsetX, player.getPosy() + windowOffsetY,
							null);
				}
			}
		}

		for (GameObject tempApple : apples) {
			int tempxApple = tempApple.getPosx();
			int tempyApple = tempApple.getPosy();
			g.drawImage(apple, tempxApple + windowOffsetX, tempyApple + windowOffsetY, null);
		}

		// Segments
		g.setColor(Color.RED);
		for (int i = 0; i <= 20; i++) {
			g.drawLine(0 + windowOffsetX, i * 1000 + windowOffsetY, 20 * 1000 + windowOffsetX,
					i * 1000 + windowOffsetY);
		}
		for (int j = 0; j <= 20; j++) {
			g.drawLine(j * 1000 + windowOffsetX, 0 + windowOffsetY, j * 1000 + windowOffsetX,
					20 * 1000 + windowOffsetY);
		}

	}
}