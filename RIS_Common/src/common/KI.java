package common;

import java.util.ArrayList;

public class KI implements Runnable {

	World world;

	public KI(World world) {
		this.world = world;
	}

	GameObject player;
	GameObject ki;
	double closestPlayerDist = 100000;
	int closestPlayerID;

	int Hx = 0;
	int Hy = 0;

	int directionToUserX = 0;
	int directionToUserY = 0;

	int directionFromHX = 0;
	int directionFromHY = 0;

	double weight1 = 0;
	double weight2 = 0;

	int threshholdDistancePlayerKI = 50;
	int threshholdDistanceObstacleKI = 70;

	double finalDirectionX = 0;
	double finalDirectionY = 0;

	boolean collision = true;

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		while (true) {
			if (world.getPlayerID() < 2) {
				updateKI();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void updateKI() {
		CollisionDetection collisionDetection = new CollisionDetection();
		GameObject ki_temp;
		closestPlayerDist = 100000;
		if (world.getEnemy() != null) {
			GameObject ki = new GameObject(world.getEnemy());

			for (GameObject p : world.getPlayers()) {
				double tempDist = dist(p, ki);
				int tempID = p.getID();

				if (tempDist < closestPlayerDist) {
					closestPlayerDist = tempDist;
					closestPlayerID = tempID;
				}
			}

			player = world.findPlayer(closestPlayerID);

			ClosestWall cw = findClosestWall(ki);
			Hx = cw.getMinX();
			Hy = cw.getMinY();
			directionToUserX = player.getPosx() - ki.getPosx();
			directionToUserY = player.getPosy() - ki.getPosy();

			directionFromHX = ki.getPosx() - Hx;
			directionFromHY = ki.getPosy() - Hy;

			double distPlayerKI = dist(player, ki);

			if (distPlayerKI > 30) {
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

				System.out.println("weight1 (player-ki) " + weight1);
				System.out.println("weight2 (ki-objective) " + weight2);

				do {
					ki_temp = new GameObject(ki);

					if (Math.random() > 0.6) {
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

				} while (collision == true);
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

	private double dist(int Hx, int Hy, GameObject ki) {
		return Math.sqrt(Math.pow((Hx - ki.getPosx()), 2) + Math.pow((Hy - ki.getPosy()), 2));
	}

	public double dist(GameObject object1, GameObject object2) {
		return Math.sqrt(Math.pow((object1.getPosx() - object2.getPosx()), 2)
				+ Math.pow((object1.getPosy() - object2.getPosy()), 2));
	}

}
