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
	BufferedImage playerImage;
	int playerID;
	LinkedList<Player> players = new LinkedList<Player>();

	public UpdateGraphic(World world) throws IOException {
		this.world = world;
		playerImage = ImageIO.read(getClass().getResource("worm.png"));
		this.playerID = world.getPlayerID();
	}

	public void paintComponent(Graphics g) {
		players = world.getPlayers();
		for(Player p : players) {
			int tempx = p.getPosx();
			int tempy = p.getPosy();
			g.drawImage(playerImage,tempx,tempy,null);	
		}
		
	}
}