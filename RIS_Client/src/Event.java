import java.util.PriorityQueue;
import java.util.Random;

import common.GameObject;
import common.World;

public class Event implements Comparable<Event> {

	public long time; 
	UpdateGraphic graphic;
	PriorityQueue<Event> queue;
	World world;
	int type;
	int duration;
	Random random;

	public Event(World world, UpdateGraphic ug, PriorityQueue<Event> queue, Integer type, Integer duration) {
		this.time = System.currentTimeMillis() + duration;
		this.graphic = ug;
		this.queue = queue;
		this.type = type;
		this.world = world;
		random = new Random();
	}

	public void execute() {
		if (type == 0) { // Render Event
			graphic.repaint();
			Event e = new Event(world, graphic, queue, 0, 16); // circa 60Hz = 16 ms
			queue.add(e);
		}
		if (type == 1) { // Apple Event
			int rnd1 = random.nextInt(200 + 1 - 100) + 100;
			int rnd2 = random.nextInt(200 + 1 - 100) + 100;
			GameObject apple = new GameObject(20, rnd1, rnd2, 50, true);
			world.addObjectToWorld(apple);
			world.triggerPosChange(apple);
		}
	}

	@Override
	public int compareTo(final Event i) {
		return Long.compare(this.time, i.time);
	}
}
