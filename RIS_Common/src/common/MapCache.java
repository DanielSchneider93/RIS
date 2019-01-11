package common;

import java.util.ArrayList;

public class MapCache implements Runnable {
	World world;
	ArrayList<WorldSegment> segmentList;
	ArrayList<WorldSegment> cache;
	int segmentSize = 20;
	WorldSegment emptySegment;

	public MapCache(World world) {
		this.world = world;
		segmentList = world.getSegmentList();
		cache = world.getCache();
		generateEmptySegment();
	}

	private void generateEmptySegment() {
		emptySegment = new WorldSegment();
		emptySegment.setX(0);
		emptySegment.setY(0);
		emptySegment.setID(999); //999 = empty Segment, dont check for collision
	}

	public void checkSegment() {
		int playerX = world.getPlayerPosX();
		int playerY = world.getPlayerPosY();
		int segmentID = -1;

		for (WorldSegment ws : segmentList) {
			if (playerX >= ws.getX() && playerX <= ws.getX() + ws.getSize()) {
				if (playerY >= ws.getY() && playerY <= ws.getY() + ws.getSize()) {
					// player is in that segment
					segmentID = ws.getID();
					break;
				}
			}
		}

		if (segmentID == -1) {
			System.out.println("cant find segment ");
		}

		cache.clear();

		WorldSegment mid = getSegmentWithID(segmentID);
		cache.add(mid);
		WorldSegment up = getSegmentWithID(segmentID - 1);
		cache.add(up);
		WorldSegment down = getSegmentWithID(segmentID + 1);
		cache.add(down);

		WorldSegment left = getSegmentWithID(segmentID - segmentSize);
		cache.add(left);
		WorldSegment leftdown = getSegmentWithID(segmentID - segmentSize + 1);
		cache.add(leftdown);
		WorldSegment leftup = getSegmentWithID(segmentID - segmentSize - 1);
		cache.add(leftup);

		WorldSegment right = getSegmentWithID(segmentID + segmentSize);
		cache.add(right);
		WorldSegment rightdown = getSegmentWithID(segmentID + segmentSize + 1);
		cache.add(rightdown);
		WorldSegment rightup = getSegmentWithID(segmentID + segmentSize - 1);
		cache.add(rightup);
		
		for(WorldSegment w : cache)
		{
			if(w.getID() != 999) {
				w.setActive(true);
			}
		}
	}

	public WorldSegment getSegmentWithID(int id) {
		if(id >= 0 && id < 200) {
			return segmentList.get(id);
		}
		else {
			return emptySegment;
		}
	}
	
	public void updateManual() {
		checkSegment();
		GameObject mapCacheTemp = new GameObject(99, 0, 0, 0, false); // 99 = map id
		mapCacheTemp.setCache(cache);
		world.triggerPosChange(mapCacheTemp);
	}
	

	@Override
	public void run() {
		while (true) {
			checkSegment();
			GameObject mapCacheTemp = new GameObject(99, 0, 0, 0, false); // 99 = map id
			mapCacheTemp.setCache(cache);
			world.triggerPosChange(mapCacheTemp);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
