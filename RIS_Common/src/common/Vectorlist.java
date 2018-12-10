package common;
import java.util.Vector;

public class Vectorlist {
    // Vector to store active clients 
	static Vector<Manager> clientlist = new Vector<>();
	private Manager handlerThreads;

	public static Vector<Manager> getClientlist() {
		return clientlist;
	}

	public void add(Manager handlerThread) {
		this.handlerThreads = handlerThread;
		clientlist.add(handlerThreads);
	} 
}
