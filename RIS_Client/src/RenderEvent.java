public class RenderEvent implements Comparable<RenderEvent>{

	public long time = System.currentTimeMillis() + 16;  //circa 60Hz
	UpdateGraphic graphic;
	EventQueueThread queue;

	public RenderEvent(UpdateGraphic ug) {
		this.graphic = ug;
	}

	public void execute() {
		graphic.repaint();
	}

	@Override
	public int compareTo(final RenderEvent i) {
		return Long.compare(this.time, i.time);
	}
}
