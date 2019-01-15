import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import common.GameObject;
import common.GenerateWorld;
import common.World;
import common.WorldSegment;

public class UpdateGraphic extends JComponent {
	private static final long serialVersionUID = 1L;
	private World world;
	@SuppressWarnings("unused")
	private GenerateWorld gw;
	private GameObject enemy;

	private int mapSizeX;
	private int mapSizeY;
	private int segmentSize;
	private int playerID;

	private BufferedImage player1_l;
	private BufferedImage player1_r;
	private BufferedImage player2_l;
	private BufferedImage player2_r;
	private BufferedImage pizza;
	private BufferedImage grass;
	private BufferedImage wall;
	private BufferedImage trap;
	private BufferedImage trap_ui;
	private BufferedImage block;
	private BufferedImage enemy_right;
	private BufferedImage enemy_left;
	private BufferedImage bar;
	private BufferedImage playerBarSegment;
	private BufferedImage enemyBarSegment;
	private BufferedImage bombUIbg;
	private BufferedImage game_over;
	private BufferedImage end;

	int windowOffsetX = 0;
	int windowOffsetY = 0;
	
	private int staticPlayerPos = 400;

	private LinkedList<GameObject> players;
	private LinkedList<GameObject> foods;
	private LinkedList<GameObject> bombs;
	private ArrayList<WorldSegment> cache;

	public UpdateGraphic(World world, GenerateWorld gw) throws IOException {
		this.world = world;
		this.gw = gw;

		player1_l = ImageIO.read(getClass().getResource("player1_l.png"));
		player1_r = ImageIO.read(getClass().getResource("player1_r.png"));
		player2_l = ImageIO.read(getClass().getResource("player2_l.png"));
		player2_r = ImageIO.read(getClass().getResource("player2_r.png"));
		pizza = ImageIO.read(getClass().getResource("pizza.png"));
		grass = ImageIO.read(getClass().getResource("gras.png"));
		wall = ImageIO.read(getClass().getResource("wall.png"));
		trap = ImageIO.read(getClass().getResource("trap.png"));
		trap_ui = ImageIO.read(getClass().getResource("trap_ui.png"));
		block = ImageIO.read(getClass().getResource("block.png"));
		enemy_right = ImageIO.read(getClass().getResource("enemy.png"));
		enemy_left = ImageIO.read(getClass().getResource("enemy_left.png"));
		bar = ImageIO.read(getClass().getResource("bar.png"));
		playerBarSegment = ImageIO.read(getClass().getResource("playerBarSegment.png"));
		enemyBarSegment = ImageIO.read(getClass().getResource("enemyBarSegment.png"));
		bombUIbg = ImageIO.read(getClass().getResource("bomb_ui_bg.png"));
		game_over = ImageIO.read(getClass().getResource("game.png"));
		end = ImageIO.read(getClass().getResource("end.png"));

		this.playerID = world.getPlayerID();
		cache = world.getCache();

		mapSizeX = gw.getHowMuchSegmentX();
		mapSizeY = gw.getHowMuchSegmentY();
		segmentSize = gw.getSegmentSize();
	}

	public void paintComponent(Graphics g) {
		players = new LinkedList<GameObject>(world.getPlayers());
		foods = new LinkedList<GameObject>(world.getApples());
		cache = new ArrayList<WorldSegment>(world.getCache());
		bombs = new LinkedList<GameObject>(world.getBombs());
		enemy = world.getEnemy();

		BufferedImage playerImage;
		BufferedImage playerImage_r;

		// Map Borders
		for (int i = 0; i <= mapSizeX * 10; i++) {
			g.drawImage(block, ((i - 1) * segmentSize) + windowOffsetX, -segmentSize + windowOffsetY, null);
		}
		for (int i = 0; i <= mapSizeY * 10; i++) {
			g.drawImage(block, -segmentSize + windowOffsetX, ((i - 1) * segmentSize) + windowOffsetY, null);
		}
		for (int i = 0; i <= mapSizeX * 10; i++) {
			g.drawImage(block, ((i - 1) * segmentSize) + windowOffsetX, mapSizeX * 1000 + windowOffsetY, null);
		}
		for (int i = 0; i <= mapSizeY * 10; i++) {
			g.drawImage(block, mapSizeX * 1000 + windowOffsetX, ((i - 1) * segmentSize) + windowOffsetY, null);
		}
		g.drawImage(block, mapSizeX * 1000 + windowOffsetX, mapSizeY * 1000 + windowOffsetY, null);
		
		// Map Segments
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

		// Food
		for (GameObject food : foods) {
			int tempxApple = food.getPosx();
			int tempyApple = food.getPosy();
			g.drawImage(pizza, tempxApple + windowOffsetX, tempyApple + windowOffsetY, null);
		}
		
		// Bombs
		for (GameObject bomb : bombs) {
			int trapX = bomb.getPosx();
			int trapY = bomb.getPosy();
			g.drawImage(trap, trapX + windowOffsetX, trapY + windowOffsetY, null);
		}

		// Players
		for (GameObject player : players) {
			if (player.getID() == 1) {
				playerImage = player1_l;
				playerImage_r = player1_r;
			} else {
				playerImage = player2_l;
				playerImage_r = player2_r;
			}
			
			if (playerID == player.getID()) {
				int hp = player.getHealth();
				
				for (int x = 0; x < hp; x++) {
					g.drawImage(playerBarSegment, staticPlayerPos + (x * 10), staticPlayerPos - 13, null);
				}
				if (player.getDirection() == 0) { 	// Look left
					g.drawImage(playerImage, staticPlayerPos, staticPlayerPos, null);
					g.drawImage(bar, staticPlayerPos, staticPlayerPos - 15, null);
				} else { 							// Look Right
					g.drawImage(playerImage_r, staticPlayerPos, staticPlayerPos, null);
					g.drawImage(bar, staticPlayerPos, staticPlayerPos - 15, null);
				}
			} else {
				int hp = player.getHealth();
				
				for (int x = 0; x < hp; x++) {
					g.drawImage(playerBarSegment, player.getPosx() + windowOffsetX + (x * 10), player.getPosy() + windowOffsetY - 13, null);
				}
				if (player.getDirection() == 0) { 	// Look left
					g.drawImage(playerImage, player.getPosx() + windowOffsetX, player.getPosy() + windowOffsetY, null);
					g.drawImage(bar, player.getPosx() + windowOffsetX, player.getPosy() + windowOffsetY - 15, null);
				} else { 							// Look Right
					g.drawImage(playerImage_r, player.getPosx() + windowOffsetX, player.getPosy() + windowOffsetY, null);
					g.drawImage(bar, player.getPosx() + windowOffsetX, player.getPosy() + windowOffsetY - 15, null);
				}
			}
		}
		
		// Enemy
		if (enemy != null) {
			int enemy_hp = enemy.getHealth();
			for (int x = 0; x < enemy_hp; x++) {
				g.drawImage(enemyBarSegment, enemy.getPosx() + windowOffsetX + x, enemy.getPosy() + windowOffsetY - 13,	null);
			}
			if (enemy.getDirection() == 0) { 		// Look left
				g.drawImage(enemy_left, enemy.getPosx() + windowOffsetX, enemy.getPosy() + windowOffsetY, null);
				g.drawImage(bar, enemy.getPosx() + windowOffsetX, enemy.getPosy() + windowOffsetY - 15, null);
			} else {								// Look Right
				g.drawImage(enemy_right, enemy.getPosx() + windowOffsetX, enemy.getPosy() + windowOffsetY, null);
				g.drawImage(bar, enemy.getPosx() + windowOffsetX, enemy.getPosy() + windowOffsetY - 15, null);
			}
		}

		// Bomb UI
		int counter = 3;
		for (GameObject b : world.getBombs()) {
			if (b.getHealth() == playerID) {
				counter--;
			}
		}
		g.drawImage(bombUIbg, 0, 0, null);
		for (int x = 0; x < counter; x++) {
			g.drawImage(trap_ui, x * 40, 0, null);
		}
		
		//	Game Over
		GameObject play = world.findPlayer(playerID);
		if (play.getHealth() < 1) {
			g.drawImage(game_over, 300, 300, null);
		}
		
		// Game Won Screen
		if (enemy != null) {
			if (enemy.getHealth() < 1) {
				g.drawImage(end, 0, 0, null);
			}
		}

		/*
		 * // Segment Lines 
		 * g.setColor(Color.RED); for (int i = 0; i <= 20; i++) {
		 * g.drawLine(0 + windowOffsetX, i * 1000 + windowOffsetY, 20 * 1000 +
		 * windowOffsetX, i * 1000 + windowOffsetY); } for (int j = 0; j <= 20; j++) {
		 * g.drawLine(j * 1000 + windowOffsetX, 0 + windowOffsetY, j * 1000 +
		 * windowOffsetX, 20 * 1000 + windowOffsetY); }
		 */
	}
}