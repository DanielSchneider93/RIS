import java.util.PriorityQueue;

public class EventQueueThread implements Runnable {
	
	private InteractionEvent ie;
	private PriorityQueue<InteractionEvent> queue = new PriorityQueue<InteractionEvent>();
	private UpdateGraphic updategraphic;
	
	public EventQueueThread(UpdateGraphic ug) {
		this.updategraphic = ug;
		
		ie = new InteractionEvent(updategraphic);
		
		// Fill queue with events from World (Item Spawns...)
		queue.add(ie);
		
		System.out.println("first Event added");
		//run();
	}

	public void add(InteractionEvent event) {
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
						System.out.println("next event added");
						InteractionEvent e = new InteractionEvent(updategraphic);
						queue.add(e);
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

