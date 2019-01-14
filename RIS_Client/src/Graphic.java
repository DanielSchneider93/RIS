import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import common.CollisionDetection;
import common.GameObject;
import common.GenerateWorld;
import common.KI;
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
	boolean pressed = false;

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
		if (e.getKeyCode() == KeyEvent.VK_A && !pressed) {

			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false, 0);
			GameObject newPlayer = new GameObject(playerID, player.getPosx() - playerSpeed, player.getPosy(),
					player.getCollisonRadius(), false, 0);
			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosx(player.getPosx() - playerSpeed);
				draw.windowOffsetX += playerSpeed;
			}
			player.setDirection(0);
			world.triggerPosChange(player);
		} else if (e.getKeyCode() == KeyEvent.VK_D && !pressed) {

			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false, 0);
			GameObject newPlayer = new GameObject(playerID, player.getPosx() + playerSpeed, player.getPosy(),
					player.getCollisonRadius(), false, 0);
			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosx(player.getPosx() + playerSpeed);
				draw.windowOffsetX -= playerSpeed;
			}
			player.setDirection(1);
			world.triggerPosChange(player);
		} else if (e.getKeyCode() == KeyEvent.VK_S && !pressed) {
			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false, 0);
			GameObject newPlayer = new GameObject(playerID, player.getPosx(), player.getPosy() + playerSpeed,
					player.getCollisonRadius(), false, 0);

			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosy(player.getPosy() + playerSpeed);
				draw.windowOffsetY -= playerSpeed;
				world.triggerPosChange(player);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_W && !pressed) {

			GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),
					player.getCollisonRadius(), false, 0);
			GameObject newPlayer = new GameObject(playerID, player.getPosx(), player.getPosy() - playerSpeed,
					player.getCollisonRadius(), false, 0);

			if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
				player.setPosy(player.getPosy() - playerSpeed);
				draw.windowOffsetY += playerSpeed;
				world.triggerPosChange(player);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_SPACE && !pressed) {
			boolean isBombAtPlayerPos = false;

			for (GameObject bomb : world.getBombs()) {
				if (bomb.getPosx() == player.getPosx()) {
					if (bomb.getPosy() == player.getPosy()) {
						isBombAtPlayerPos = true;
					}
				}
			}
			if (!isBombAtPlayerPos) {
				GameObject bomb = new GameObject(bombID, player.getPosx(), player.getPosy(), 100, true, 0);
				world.triggerPosChange(bomb);
				bombID++;
			}
		} 
		pressed = true;
	}

	public boolean checkMapBoundarys(GameObject newPlayer) {
		boolean isInMap = false;
		if (newPlayer.getPosx() >= 0
				&& newPlayer.getPosx() < (gw.getSegmentSize() * 10 * gw.getHowMuchSegmentX()) - 50) {
			if (newPlayer.getPosy() >= 0
					&& newPlayer.getPosy() < (gw.getSegmentSize() * 10 * gw.getHowMuchSegmentY()) - 50) {
				isInMap = true;
			}
		}
		return isInMap;
	}

	public void keyReleased(KeyEvent e) {
		pressed = false;
	}

	public void keyTyped(KeyEvent e) {
	}
}
