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
	public World world;
	BufferedImage playerImage;
	BufferedImage playerImage_r;
	BufferedImage apple;
	BufferedImage grass;
	BufferedImage wall;

	int playerID;
	LinkedList<GameObject> players = new LinkedList<GameObject>();
	LinkedList<GameObject> apples = new LinkedList<GameObject>();

	public UpdateGraphic(World world) throws IOException {
		this.world = world;

		playerImage = ImageIO.read(getClass().getResource("worm.png"));
		playerImage_r = ImageIO.read(getClass().getResource("worm_r.png"));
		apple = ImageIO.read(getClass().getResource("apple.png"));
		grass = ImageIO.read(getClass().getResource("gras.png"));
	    wall = ImageIO.read(getClass().getResource("wall.png"));;

		this.playerID = world.getPlayerID();
	}

	public void paintComponent(Graphics g) {
		//g.setColor(Color.RED);
		
		ArrayList<WorldSegment> segmentList = world.getSegmentList();
		
		for(WorldSegment s : segmentList)
		{
			ArrayList<Integer> list = s.getList();
			int posx = s.getX();
			int posy = s.getY();
			int counter = 0;
			
			for(Integer i : list) {
				counter++;
				posx += 100;
				
				if(counter == 9) {
					posy += 100;
					posx = s.getX();
				}
						
				if(i == 0) {
					g.drawImage(grass, posx, posy, null);
				}
				if(i == 1) {
					g.drawImage(wall, posx, posy, null);
				}
			}
			
		}
		
		
		
		players = world.getPlayers();
		for (GameObject player : players) {
			int tempx = player.getPosx();
			int tempy = player.getPosy();
			//int radius = player.getCollisonRadius();

			if (player.getDirection() == 0) { // Look left
				g.drawImage(playerImage, tempx, tempy, null);
				//g.fillOval(tempx, tempy, radius, radius);
			} else { // Look Right
				g.drawImage(playerImage_r, tempx, tempy, null);
				//g.fillOval(tempx, tempy, radius, radius);
			}
		}

		apples = world.getApples();
		for (GameObject tempApple : apples) {
			int tempxApple = tempApple.getPosx();
			int tempyApple = tempApple.getPosy();
			int tempRadius = tempApple.getCollisonRadius();
			g.drawImage(apple, tempxApple, tempyApple, null);
			//g.fillOval(tempxApple, tempyApple, tempRadius, tempRadius);
		}
	}
}