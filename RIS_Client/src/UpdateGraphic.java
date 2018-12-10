import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JComponent;

import common.Player;
import common.World;

@SuppressWarnings("serial")
public class UpdateGraphic extends JComponent {
	public World world;
	Player player;
	@SuppressWarnings("rawtypes")
	Class playerclass;
	Player p;

	public UpdateGraphic(World world) throws IOException {
		this.world = world;
		player = new Player(0);
		playerclass = player.getClass();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("in updateGraphic paintComponent");

		LinkedList<Object> list = world.getWorld();
		System.out.println("world list size in updateGraphic " + list.size());

		for (Object o : list) {
			System.out.println(o);
			@SuppressWarnings("rawtypes")
			Class c = o.getClass();

			if (c == playerclass) {
				p = (Player)o;
				System.out.println("player in updateGraphic " + p);
			}

			int x = p.getPosx();
			int y = p.getPosy();
			BufferedImage bi = p.getPlayer();

			g.drawImage(bi,x,y,null);	
		}	
	}
}