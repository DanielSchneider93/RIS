import java.util.PriorityQueue;

import common.KI;
import common.World;

public class EventQueueThread implements Runnable {
	
	private Event ie;
	private PriorityQueue<Event> queue = new PriorityQueue<Event>();
	private UpdateGraphic updategraphic;
	private World world;
	KI ki;
	
	public EventQueueThread(UpdateGraphic ug, World world1, KI ki) {
		this.updategraphic = ug;
		this.world = world1;
		this.ki = ki;
		
		ie = new Event(world, updategraphic, queue, 0, 16,ki); //0 = RenderEvent 16 = 16 ms -> 60 Hz
		queue.add(ie);
		System.out.println("Started Rendering Event");
		
		Event e = new Event(world, updategraphic, queue, 1, 2000,ki);
		queue.add(e);
		System.out.println("Started Apple Event");
		
		if(world.getPlayerID() ==1) {
			startKI();
		}
	}

	public void add(Event event) {
		synchronized (queue) {
			queue.offer(event);
			queue.notify();
		}
	}
	
	public void startKI() {
		Event k = new Event(world, updategraphic, queue, 2, 500 , ki);
		queue.add(k);
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

