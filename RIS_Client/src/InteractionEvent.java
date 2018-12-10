public class InteractionEvent implements Comparable<InteractionEvent>{

	public long time = System.currentTimeMillis() + 10000;
	UpdateGraphic graphic;
	EventQueueThread queue;

	public InteractionEvent(UpdateGraphic ug) {
		this.graphic = ug;
	}

	public void execute() {
		//add to world
	}

	@Override
	public int compareTo(final InteractionEvent i) {
		return Long.compare(this.time, i.time);
	}
}
