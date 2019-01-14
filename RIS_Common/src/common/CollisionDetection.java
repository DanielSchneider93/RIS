package common;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionDetection {
	LinkedList<GameObject> worldcopy;
	GameObject toCheck;
	GameObject currentWorldObject;
	int toCheckID;
	GameObject collisionWithThisObject;
	ArrayList<WorldSegment> cache_local = null;
	private int wall = 1;
	boolean fast = false;
	World world;

	public boolean detect(GameObject currentWorldObject, GameObject objectFromMessage,
			LinkedList<GameObject> worldCopyToCheckCollision, ArrayList<WorldSegment> cache, boolean fast, World world) {
		this.fast = fast;
		this.world = world;

		boolean collisionDetected = false;

		this.currentWorldObject = currentWorldObject;
		this.toCheck = objectFromMessage;
		if (cache != null) {
			this.cache_local = new ArrayList<WorldSegment>(cache);
		}
		toCheckID = toCheck.getID();

		double radius = toCheck.getCollisonRadius() / 2;
		double posx = toCheck.getPosx() + radius;
		double posy = toCheck.getPosy() + radius;

		CollisionCircle ccToCheck = new CollisionCircle(radius, posx, posy);

		if (!fast) {
			worldcopy = new LinkedList<GameObject>(worldCopyToCheckCollision);
			worldcopy.remove(currentWorldObject);
			worldcopy.add(objectFromMessage);

			int count = worldcopy.size();

			for (int z = 0; z < count; z++) {
				GameObject tempObject = worldcopy.get(z);
				boolean skip = false;
				if (tempObject.getID() != toCheck.getID()) {
					if (toCheck.getID() <= 10 && tempObject.getID() > 1000) {
						skip = true;
					}
					if (!skip) {
						double tempradius = tempObject.getCollisonRadius() / 2;
						double tempPosx = tempObject.getPosx() + tempradius;
						double tempPosy = tempObject.getPosy() + tempradius;

						CollisionCircle ccTemp = new CollisionCircle(tempradius, tempPosx, tempPosy);
						boolean collison = hasCollision(ccToCheck, ccTemp);

						if (collison) {
							collisionDetected = true;
							collisionWithThisObject = tempObject;
							
							// if collision with apple -> add player health
							if(collisionWithThisObject.getID() < 30 && collisionWithThisObject.getID() > 20) {
								if(toCheck.getID() < 10) {
									objectFromMessage.setHealth(objectFromMessage.getHealth() + 1);
									//world.updateWorld.shareWorldWithClients();
									System.out.println("added 1 healt to player hp");
								}
							}
							
							if(collisionWithThisObject.getID() > 1000 && toCheck.getID() == 50) {
								objectFromMessage.setHealth(objectFromMessage.getHealth() - 1);
							}
							
							
							break;
						}
					}
				}
			}

		} else

		{// Fast check for client -> only the segment that the player is standing on
			if (cache != null) {

				int offset = 100;

				for (WorldSegment ws : cache_local) {

					WorldSegment tempSegment = ws;

					if (tempSegment.getID() != 999) {

						int counterx = 1;
						int countery = 0;
						int segmentX = tempSegment.getX();
						int segmentY = tempSegment.getY();

						int elementY = 0;

						for (int i = 0; i < tempSegment.getList().size(); i++) {
							int tempInt = tempSegment.getList().get(i);

							int elementX = segmentX + ((counterx - 1) * offset);

							if (countery == 10) {
								elementY += offset;
								countery = 0;
							}
							if (counterx % 10 == 0) {
								counterx = 0;
							}
							counterx++;
							countery++;

							if (tempInt == wall) {
								CollisionCircle cc = new CollisionCircle(50, elementX + 50, elementY + 50 + segmentY);
								boolean coll = hasCollision(ccToCheck, cc);
								if (coll) {
									collisionDetected = true;
									collisionWithThisObject = null;
								}
							}
						}
					}
				}
			}
		}

		return collisionDetected;
	}

	public GameObject getCollisionWithThisObject() {
		return collisionWithThisObject;
	}

	public boolean hasCollision(CollisionCircle circle1, CollisionCircle circle2) {
		double xDiff = circle1.getX() - circle2.getX();
		double yDiff = circle1.getY() - circle2.getY();
		double distanceSquared = xDiff * xDiff + yDiff * yDiff;
		boolean collision = distanceSquared < (circle1.getRadius() + circle2.getRadius())
				* (circle1.getRadius() + circle2.getRadius());
		return collision;
	}

}
