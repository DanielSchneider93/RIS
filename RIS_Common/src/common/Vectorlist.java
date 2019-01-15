package common;
import java.util.Vector;

public class Vectorlist {
	private Vector<Manager> clientlist = new Vector<>();
	private Manager handlerThreads;

	public Vector<Manager> getClientlist() {
		return clientlist;
	}

	public void add(Manager handlerThread) {
		this.handlerThreads = handlerThread;
		clientlist.add(handlerThreads);
	} 
}
