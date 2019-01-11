import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import common.CollisionDetection;
import common.GameObject;
import common.GenerateWorld;
import common.MapCache;
import common.World;

public class Graphic extends JFrame implements KeyListener {
	private UpdateGraphic draw;
	GenerateWorld gw;
	public World world;
	int playerID;
	GameObject player;
	int playerSpeed = 50;
	MapCache mc;
	CollisionDetection collisionDetection = new CollisionDetection();
	boolean onWindow = false;
	int bombID = 1000;

	public Graphic(World world, MapCache mc, GenerateWorld gw) throws IOException {
		this.world = world;
		this.mc = mc;
		this.gw = gw;
		this.draw = new UpdateGraphic(world, gw);
		this.playerID = world.getPlayerID();
		this.player = world.findPlayer(playerID);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setTitle("Daniel Schneider - RIS");
		// important
		setResizable(false);
		add(draw);
		setSize(1000, 1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public UpdateGraphic getUpdategraphic() {
		return draw;
	}

	public boolean fastMapCollisionCheck(GameObject oldPlayer, GameObject newPlayer) {

		LinkedList<GameObject> worldcopy = new LinkedList<GameObject>(world.getWorld());
		boolean collision = collisionDetection.detect(oldPlayer, newPlayer, worldcopy, world.getCache(), true);
		if (collisionDetection.getCollisionWithThisObject() == null && collision == true) {
			System.out.println("collision at input check");
			return true;

		} else {
			return false;
		}

	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {

			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false);
			GameObject newPlayer = new GameObject(playerID, player.getPosx() - playerSpeed, player.getPosy(),
					player.getCollisonRadius(), false);
			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosx(player.getPosx() - playerSpeed);
				draw.windowOffsetX += playerSpeed;
				player.setDirection(0);
				world.triggerPosChange(player);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_D) {

			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false);
			GameObject newPlayer = new GameObject(playerID, player.getPosx() + playerSpeed, player.getPosy(),
					player.getCollisonRadius(), false);
			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosx(player.getPosx() + playerSpeed);
				draw.windowOffsetX -= playerSpeed;
				player.setDirection(1);
				world.triggerPosChange(player);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false);
			GameObject newPlayer = new GameObject(playerID, player.getPosx(), player.getPosy() + playerSpeed,
					player.getCollisonRadius(), false);

			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosy(player.getPosy() + playerSpeed);
				draw.windowOffsetY -= playerSpeed;
				world.triggerPosChange(player);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_W) {

			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false);
			GameObject newPlayer = new GameObject(playerID, player.getPosx(), player.getPosy() - playerSpeed,
					player.getCollisonRadius(), false);

			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosy(player.getPosy() - playerSpeed);
				draw.windowOffsetY += playerSpeed;
				world.triggerPosChange(player);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			GameObject bomb = new GameObject(bombID, player.getPosx(), player.getPosy());
			world.triggerPosChange(bomb);
			bombID++;
		}
	}
	
	public boolean checkMapBoundarys(GameObject newPlayer) {
		boolean isInMap = false;
		if(newPlayer.getPosx() >= 0 && newPlayer.getPosx() <= (gw.getSegmentSize() * gw.getHowMuchSegmentX())) {
			if (newPlayer.getPosy() >= 0 && newPlayer.getPosy() <= (gw.getSegmentSize() * gw.getHowMuchSegmentY())) {
				isInMap = true;
			}
		}
		return isInMap;
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}
}
