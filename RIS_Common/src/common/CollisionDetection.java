package common;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionDetection {
	LinkedList<GameObject> worldcopy;
	GameObject toCheck;
	GameObject currentWorldObject;
	int toCheckID;
	GameObject collisionWithThisObject;
	ArrayList<WorldSegment> cache = null;
	private int wall = 1;
	boolean fast = false;

	public boolean detect(GameObject currentWorldObject, GameObject objectFromMessage,
			LinkedList<GameObject> worldCopyToCheckCollision, ArrayList<WorldSegment> cache, boolean fast) {
		this.fast = fast;

		boolean collisionDetected = false;

		this.currentWorldObject = currentWorldObject;
		this.toCheck = objectFromMessage;
		this.cache = cache;
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
				if (tempObject.getID() != toCheck.getID()) {

					double tempradius = tempObject.getCollisonRadius() / 2;
					double tempPosx = tempObject.getPosx() + tempradius;
					double tempPosy = tempObject.getPosy() + tempradius;

					CollisionCircle ccTemp = new CollisionCircle(tempradius, tempPosx, tempPosy);
					boolean collison = hasCollision(ccToCheck, ccTemp);

					if (collison) {
						collisionDetected = true;
						collisionWithThisObject = tempObject;
						break;
					}
				}
			}

		} else {// Fast check for client -> only the segment that the player is standing on
			if (cache != null) {
				int counterx = 1;
				int countery = 0;
				int offset = 100;

				WorldSegment tempSegment = cache.get(0);
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

					//elementY = segmentY + elementY;

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
