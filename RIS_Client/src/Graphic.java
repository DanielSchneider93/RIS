import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

import common.CollisionDetection;
import common.GameObject;
import common.GenerateWorld;
import common.World;

public class Graphic extends JFrame implements KeyListener {
	private UpdateGraphic draw;
	private GenerateWorld gw;
	private World world;
	private int playerID;
	private GameObject player;
	private int playerSpeed = 50;
	private CollisionDetection collisionDetection;
	private int bombID = 1000;
	private boolean pressed = false;

	public Graphic(World world, GenerateWorld gw) throws IOException {
		this.world = world;
		this.gw = gw;
		this.draw = new UpdateGraphic(world, gw);
		this.playerID = world.getPlayerID();

		collisionDetection = new CollisionDetection();

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setTitle("Daniel Schneider - RIS");
		// important, is not resizable at the Moment
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
		boolean collision = collisionDetection.detect(oldPlayer, newPlayer, worldcopy, world.getCache(), true, world);

		if (collisionDetection.getCollisionWithThisObject() == null && collision == true) {
			return true; // collision
		} else {
			return false;
		}
	}

	public void keyPressed(KeyEvent e) {
		this.player = world.findPlayer(playerID);
		if (player.getHealth() > 0) {
			
			if (e.getKeyCode() == KeyEvent.VK_A && !pressed) {

				GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),	player.getCollisonRadius(), false, 0);
				GameObject newPlayer = new GameObject(playerID, player.getPosx() - playerSpeed, player.getPosy(), player.getCollisonRadius(), false, 0);
				if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
					player.setPosx(player.getPosx() - playerSpeed);
					draw.windowOffsetX += playerSpeed;
				}
				player.setDirection(0);
				world.triggerPosChange(player);
				
			} else if (e.getKeyCode() == KeyEvent.VK_D && !pressed) {

				GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(), player.getCollisonRadius(), false, 0);
				GameObject newPlayer = new GameObject(playerID, player.getPosx() + playerSpeed, player.getPosy(), player.getCollisonRadius(), false, 0);
				if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
					player.setPosx(player.getPosx() + playerSpeed);
					draw.windowOffsetX -= playerSpeed;
				}
				player.setDirection(1);
				world.triggerPosChange(player);
				
			} else if (e.getKeyCode() == KeyEvent.VK_S && !pressed) {
				
				GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),	player.getCollisonRadius(), false, 0);
				GameObject newPlayer = new GameObject(playerID, player.getPosx(), player.getPosy() + playerSpeed, player.getCollisonRadius(), false, 0);

				if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
					player.setPosy(player.getPosy() + playerSpeed);
					draw.windowOffsetY -= playerSpeed;
					world.triggerPosChange(player);
				}

			} else if (e.getKeyCode() == KeyEvent.VK_W && !pressed) {

				GameObject oldPlayer = new GameObject(playerID, player.getPosx(), player.getPosy(),	player.getCollisonRadius(), false, 0);
				GameObject newPlayer = new GameObject(playerID, player.getPosx(), player.getPosy() - playerSpeed, player.getCollisonRadius(), false, 0);

				if (!fastMapCollisionCheck(oldPlayer, newPlayer) && checkMapBoundarys(newPlayer)) {
					player.setPosy(player.getPosy() - playerSpeed);
					draw.windowOffsetY += playerSpeed;
					world.triggerPosChange(player);
				}

			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				//check if bomb is already at that spot
				boolean isBombAtPlayerPos = false;
				for (GameObject bomb : world.getBombs()) {
					if (bomb.getPosx() == player.getPosx()) {
						if (bomb.getPosy() == player.getPosy()) {
							isBombAtPlayerPos = true;
						}
					}
				}
				//check Bomb size 
				if (!isBombAtPlayerPos) {
					int counter = 0;
					for (GameObject b : world.getBombs()) {
						if (b.getHealth() == playerID) {
							counter++;
						}
					}
					
					//add Bomb
					if (counter < 3) {
						GameObject bomb = new GameObject(bombID, player.getPosx(), player.getPosy(), 100, true,	playerID);
						world.triggerPosChange(bomb);
						bombID++;
					}
				}
			}
			pressed = true;
		}
	}

	public boolean checkMapBoundarys(GameObject newPlayer) {
		boolean isInMap = false;
		if (newPlayer.getPosx() >= 0 && newPlayer.getPosx() < (gw.getSegmentSize() * 10 * gw.getHowMuchSegmentX()) - 50) {
			if (newPlayer.getPosy() >= 0 && newPlayer.getPosy() < (gw.getSegmentSize() * 10 * gw.getHowMuchSegmentY()) - 50) {
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
