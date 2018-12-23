import java.util.PriorityQueue;

import common.World;

public class EventQueueThread implements Runnable {
	
	private Event ie;
	private PriorityQueue<Event> queue = new PriorityQueue<Event>();
	private UpdateGraphic updategraphic;
	private World world;
	
	public EventQueueThread(UpdateGraphic ug, World world1) {
		this.updategraphic = ug;
		this.world = world1;
		
		ie = new Event(world, updategraphic, queue, 0, 16); //0 = RenderEvent 16 = 16 ms -> 60 Hz
		queue.add(ie);
		System.out.println("Started Rendering Event");
		
		Event e = new Event(world, updategraphic, queue, 1, 5000);
		queue.add(e);
		System.out.println("Started Apple Event");
	}

	public void add(Event event) {
		synchronized (queue) {
			queue.offer(event);
			queue.notify();
		}
	}

	@Override
	public void run() {
		synchronized (queue) {
			while (true) {
				try {
					long timeout = queue.peek().time - System.currentTimeMillis();
					if (timeout <= 0) {
						queue.poll().execute();
					}
					else {
						queue.wait(timeout);
					}
				}  catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

