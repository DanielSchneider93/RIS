import java.util.PriorityQueue;

import common.CollisionDetection;
import common.GameObject;
import common.World;

public class Event implements Comparable<Event> {

	public long time;

	private UpdateGraphic graphic;
	private PriorityQueue<Event> queue;
	private World world;
	private int type;
	private CollisionDetection collisionDetection;
	private GameObject food;

	public Event(World world, UpdateGraphic ug, PriorityQueue<Event> queue, Integer type, Integer duration) {
		collisionDetection = new CollisionDetection();
		this.time = System.currentTimeMillis() + duration;
		this.graphic = ug;
		this.queue = queue;
		this.type = type;
		this.world = world;
	}

	public void execute() {
		if (type == 0) { // Render Event
			graphic.repaint();
			Event e = new Event(world, graphic, queue, 0, 16); // 60Hz = 16 ms
			queue.add(e);
		}
		if (type == 1) { // Food Event
			boolean collision = false;

			do { // Spawn Food in the proximity to the Player

				int minX = world.findPlayer(world.getPlayerID()).getPosx() - 500;
				int maxX = world.findPlayer(world.getPlayerID()).getPosx() + 500;
				int minY = world.findPlayer(world.getPlayerID()).getPosy() - 500;
				int maxY = world.findPlayer(world.getPlayerID()).getPosy() + 500;

				int rnd1 = minX + (int) (Math.random() * ((maxX - minX) + 1));
				int rnd2 = minY + (int) (Math.random() * ((maxY - minY) + 1));

				// dont let food spawn outside of the walls
				if (rnd1 < 0) {
					rnd1 = 10;
				}
				if (rnd2 < 0) {
					rnd2 = 10;
				}

				food = new GameObject(20 + world.getPlayerID(), rnd1, rnd2, 50, true, 0);
				collision = collisionDetection.detect(null, food, null, world.getCache(), true, world);

			} while (collision);

			world.addObjectToWorld(food);
			world.triggerPosChange(food);

			Event e = new Event(world, graphic, queue, 1, 10000);
			queue.add(e);
		}
	}

	@Override
	public int compareTo(final Event i) {
		return Long.compare(this.time, i.time);
	}
}
