package common;

import java.util.ArrayList;

public class KI implements Runnable {

	private World world;
	private GameObject player;
	private GameObject ki;
	private double closestPlayerDist = 100000;
	private int closestPlayerID;
	private ClosestWall cw;
	private int Hx = 0;
	private int Hy = 0;
	private int directionToUserX = 0;
	private int directionToUserY = 0;
	private int directionFromHX = 0;
	private int directionFromHY = 0;
	private double weight1 = 0;
	private double weight2 = 0;
	private int threshholdDistancePlayerKI = 20;
	private int threshholdDistanceObstacleKI = 60;
	private double finalDirectionX = 0;
	private double finalDirectionY = 0;
	private boolean collision = true;
	private int KiUpdateInterval = 500;
	private CollisionDetection collisionDetection;
	private GameObject ki_temp;
	private GenerateWorld gw;

	public KI(World world, GenerateWorld gw) {
		this.world = world;
		this.gw = gw;
	}

	@Override
	public void run() {
		try {
			// Start Enemy after 5 Sec.
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while (true) {
			if (world.getPlayerID() < 2) {
				if (world.getEnemy().getHealth() > 0) {
					updateKI();
					try {
						Thread.sleep(KiUpdateInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} else {
				try {
					Thread.sleep(10000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void updateKI() {
		collisionDetection = new CollisionDetection();
		closestPlayerDist = 100000;
		boolean isInMap = false;

		if (world.getEnemy() != null) {
			ki = new GameObject(world.getEnemy());

			for (GameObject p : world.getPlayers()) {
				if (p.getHealth() > 0) {
					double tempDist = dist(p, ki);
					int tempID = p.getID();

					if (tempDist < closestPlayerDist) {
						closestPlayerDist = tempDist;
						closestPlayerID = tempID;
					}
				}
			}

			player = world.findPlayer(closestPlayerID);

			cw = findClosestWall(ki);
			Hx = cw.getMinX();
			Hy = cw.getMinY();
			directionToUserX = player.getPosx() - ki.getPosx();
			directionToUserY = player.getPosy() - ki.getPosy();
			directionFromHX = ki.getPosx() - Hx;
			directionFromHY = ki.getPosy() - Hy;

			double distPlayerKI = dist(player, ki);

			// if enemy stands on Player he is dead
			if (distPlayerKI < 10) {
				player.setHealth(0);
				world.triggerPosChange(player);
			}

			if (distPlayerKI > 20) {
				if (distPlayerKI < threshholdDistancePlayerKI) {
					weight1 = 10;
				} else {
					weight1 = distPlayerKI;
				}

				double distObstacleKI = dist(Hx, Hy, ki);

				if (distObstacleKI < threshholdDistanceObstacleKI) {
					weight2 = 10000;
				} else {
					weight2 = 10000 / distObstacleKI;
				}

				finalDirectionX = weight1 * directionToUserX + weight2 * directionFromHX;
				finalDirectionY = weight1 * directionToUserY + weight2 * directionFromHY;

				do {
					do {
						ki_temp = new GameObject(ki);

						if (Math.random() > 0.5) {
							if (finalDirectionX > 0) {
								ki_temp.setPosx(ki_temp.getPosx() + 50);
								ki_temp.setDirection(1);
							} else {
								ki_temp.setPosx(ki_temp.getPosx() - 50);
								ki_temp.setDirection(0);
							}
						} else {
							if (finalDirectionY > 0) {
								ki_temp.setPosy(ki_temp.getPosy() + 50);
							} else {
								ki_temp.setPosy(ki_temp.getPosy() - 50);
							}
						}

						collision = collisionDetection.detect(ki, ki_temp, null, world.getCache(), true, world);
						isInMap = checkMapBoundarys(ki_temp);

					} while (collision == true);
				} while (isInMap == false);

				world.getEnemy().setPosx(ki_temp.getPosx());
				world.getEnemy().setPosy(ki_temp.getPosy());
				world.triggerPosChange(ki_temp);
			}
		}
	}

	public ClosestWall findClosestWall(GameObject g) {
		int tempX;
		int tempY;
		double minDist = 10000;
		int minX = 0;
		int minY = 0;
		ClosestWall cw;

		for (WorldSegment ws : world.getSegmentList()) {
			ArrayList<Integer> elements = ws.getList();
			tempX = ws.getX();
			tempY = ws.getY();

			int counter = 0;
			for (Integer i : elements) {
				counter++;
				if (i == 1) {
					double distance = dist(tempX, tempY, g);
					if (distance < minDist) {
						minDist = distance;
						minX = tempX;
						minY = tempY;
					}
				}
				tempX += 100;
				if (counter == 10) {
					tempY += 100;
					tempX = ws.getX();
					counter = 0;
				}
			}
		}
		cw = new ClosestWall(minDist, minX, minY);
		return cw;
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

	private double dist(int Hx, int Hy, GameObject ki) {
		return Math.sqrt(Math.pow((Hx - ki.getPosx()), 2) + Math.pow((Hy - ki.getPosy()), 2));
	}

	public double dist(GameObject object1, GameObject object2) {
		return Math.sqrt(Math.pow((object1.getPosx() - object2.getPosx()), 2)
				+ Math.pow((object1.getPosy() - object2.getPosy()), 2));
	}
}
