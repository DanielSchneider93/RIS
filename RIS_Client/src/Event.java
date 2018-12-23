import java.util.PriorityQueue;
import java.util.Random;

import common.Apple;
import common.World;

public class Event implements Comparable<Event> {

	public long time; // circa 60Hz
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
			Event e = new Event(world, graphic, queue, 0, 16);
			queue.add(e);
		}
		if (type == 1) { //Apple Event
			int randomNumber1 = random.nextInt(200 + 1 - 100) + 100;
			int randomNumber2 = random.nextInt(200 + 1 - 100) + 100;
			Apple apple = new Apple(randomNumber1,randomNumber2);
			world.addObjectToWorld(apple);
			//TODO: tell Server that apples exists, -> recode handler
		}

	}

	@Override
	public int compareTo(final Event i) {
		return Long.compare(this.time, i.time);
	}
}