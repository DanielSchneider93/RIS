import java.util.PriorityQueue;
import java.util.Random;

import common.GameObject;
import common.KI;
import common.World;

public class Event implements Comparable<Event> {

	public long time;
	UpdateGraphic graphic;
	PriorityQueue<Event> queue;
	World world;
	int type;
	int duration;

	public Event(World world, UpdateGraphic ug, PriorityQueue<Event> queue, Integer type, Integer duration) {
		this.time = System.currentTimeMillis() + duration;
		this.graphic = ug;
		this.queue = queue;
		this.type = type;
		this.world = world;
	}

	public void execute() {
		if (type == 0) { // Render Event
			graphic.repaint();
			Event e = new Event(world, graphic, queue, 0, 16); // circa 60Hz = 16 ms
			queue.add(e);
		}
		if (type == 1) { // Apple Event
			int tempPlayerPosX = world.getPlayerPosX();
			int tempPlayerPosY = world.getPlayerPosY();
			
			if (tempPlayerPosX < 100) {
				tempPlayerPosX += 100;
			}
			
			if (tempPlayerPosY < 100) {
				tempPlayerPosY += 100;
			}
			
			int minX = world.findPlayer(world.getPlayerID()).getPosx() - 250;
			int maxX = world.findPlayer(world.getPlayerID()).getPosx() + 250;
			
			int minY = world.findPlayer(world.getPlayerID()).getPosy() - 250;
			int maxY = world.findPlayer(world.getPlayerID()).getPosy() + 250;

			int rnd1 = minX + (int)(Math.random() * ((maxX - minX) + 1));
			int rnd2 = minY + (int)(Math.random() * ((maxY - minY) + 1));

			GameObject apple = new GameObject(20 + world.getPlayerID(), rnd1, rnd2, 50, true, 0);

			world.addObjectToWorld(apple);
			world.triggerPosChange(apple);

			Event e = new Event(world, graphic, queue, 1, 15000);
			queue.add(e);
		}
	}

	@Override
	public int compareTo(final Event i) {
		return Long.compare(this.time, i.time);
	}
}
