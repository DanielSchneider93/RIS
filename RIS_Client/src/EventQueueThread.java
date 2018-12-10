import java.util.PriorityQueue;

public class EventQueueThread implements Runnable {
	
	private RenderEvent ie;
	private PriorityQueue<RenderEvent> queue = new PriorityQueue<RenderEvent>();
	private UpdateGraphic updategraphic;
	
	public EventQueueThread(UpdateGraphic ug) {
		this.updategraphic = ug;
		ie = new RenderEvent(updategraphic);
		queue.add(ie);
		System.out.println("Started Rendering Event");
	}

	public void add(RenderEvent event) {
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
						RenderEvent e = new RenderEvent(updategraphic);
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

