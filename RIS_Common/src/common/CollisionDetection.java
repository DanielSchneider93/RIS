package common;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionDetection {
	LinkedList<GameObject> worldcopy = new LinkedList<GameObject>();
	GameObject toCheck;
	GameObject currentWorldObject;
	int toCheckID;
	GameObject collisionWithThisObject;
	ArrayList<WorldSegment> cache = null;

	public boolean detect(GameObject currentWorldObject, GameObject objectFromMessage,
			LinkedList<GameObject> worldCopyToCheckCollision, ArrayList<WorldSegment> cache) {
		boolean collisionDetected = false;

		this.worldcopy = worldCopyToCheckCollision;
		this.currentWorldObject = currentWorldObject;
		this.toCheck = objectFromMessage;
		this.cache = cache;
		toCheckID = toCheck.getID();
		worldcopy.remove(currentWorldObject);
		worldcopy.add(objectFromMessage);

		double radius = toCheck.getCollisonRadius() / 2;
		double posx = toCheck.getPosx() + radius;
		double posy = toCheck.getPosy() + radius;

		CollisionCircle ccToCheck = new CollisionCircle(radius, posx, posy);

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
				}
			}
		}

		if (cache != null) {
			int countMap = cache.size();
			System.out.println("cache size " + countMap);
			int offset = 100;
			int counterx = 1;
			int countery = 0;

			for (int y = 0; y < countMap; y++) {
				WorldSegment tempSegment = cache.get(y);

				int segmentX = tempSegment.getX();
				int segmentY = tempSegment.getY();
				int elementY = 0;

				for (Integer i : tempSegment.getList()) {

					int elementX = segmentX + ((counterx-1) * 100);
				
					if(countery == 10) {
						elementY += 100;
						countery = 0;
					}

					if (counterx % 10 == 0) {
						counterx = 0;
					}
					counterx++;
					countery++;
					
					if (i == 1)
					{
						//TODO: build collider and check
					}

					System.out.println("element " + i  + " x " + elementX + " y " + elementY);
				}

				// ccToCheck

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
