package common;

import java.util.ArrayList;

public class MapCache implements Runnable {
	private World world;
	private ArrayList<WorldSegment> segmentList;
	private ArrayList<WorldSegment> cache;
	private int segmentSize = 20;
	private WorldSegment emptySegment;

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
		emptySegment.setID(999); // 999 = empty Segment, do not check for collision
	}

	public void updateCache(int newSegment) {
		int segmentID = newSegment;

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

		for (WorldSegment w : cache) {
			if (w.getID() != 999) {
				w.setActive(true);
			}
		}
	}

	public WorldSegment getSegmentWithID(int id) {
		if (id >= 0 && id < segmentSize * segmentSize) {
			return segmentList.get(id);
		} else {
			return emptySegment;
		}
	}

	public int getPlayerSegment() {
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
			System.out.println("Can not find segment!");
		}

		return segmentID;
	}

	@Override
	public void run() {
		while (true) {
			int lastSegment = -1;
			int newSegment = getPlayerSegment();

			if (lastSegment != newSegment) {
				lastSegment = newSegment;
				updateCache(newSegment);
				world.setCache(cache);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
