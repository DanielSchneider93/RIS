package common;

import java.util.LinkedList;

public class CollisionDetection {
	LinkedList<GameObject> worldcopy = new LinkedList<GameObject>();
	GameObject toCheck;
	GameObject currentWorldObject;
	int toCheckID;
	GameObject collisionWithThisObject;
	

	public boolean detect(GameObject currentWorldObject, GameObject objectFromMessage, LinkedList<GameObject> worldCopyToCheckCollision) {
		boolean collisionDetected = false;
		this.worldcopy = worldCopyToCheckCollision;
		this.currentWorldObject = currentWorldObject;
		this.toCheck = objectFromMessage;
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
				//System.out.println("collison: " + collison + " id of object to check " +  tempObject.getID() );

				if (collison) {
					collisionDetected = true;
					collisionWithThisObject = tempObject;
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
		boolean collision = distanceSquared < (circle1.getRadius() + circle2.getRadius()) * (circle1.getRadius() + circle2.getRadius());
		return collision;
	}

}
