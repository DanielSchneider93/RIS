import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import common.Player;
import common.World;

public class UpdateGraphic extends JComponent {
	public World world;
	Player player;
	Class<?> playerclass;
	Player p;
	BufferedImage playerImage;

	public UpdateGraphic(World world) throws IOException {
		this.world = world;
		player = new Player(0);
		playerclass = player.getClass();
		playerImage = ImageIO.read(getClass().getResource("worm.png"));
	}

	public void paintComponent(Graphics g) {
		LinkedList<Object> list = world.getWorld();
		LinkedList<Object> copyList = list;
		
		for (int z = 0; z < copyList.size(); z++) {
			Object o = copyList.get(z);
			Class<?> c = o.getClass();

			if (c == playerclass) {
				p = (Player)o;
			}

			int x = p.getPosx();
			int y = p.getPosy();

			g.drawImage(playerImage,x,y,null);	
		}	
	}
}