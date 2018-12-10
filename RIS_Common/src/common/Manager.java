package common;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

//Server Manger
public class Manager implements Runnable {

	private final ObjectInputStream InputStream;
	private final ObjectOutputStream OutputStream;
	private WorkingThread workingThread;
	private boolean isClient; // true = client , false = server
	private List<Manager> clientlist;

	public Manager(ObjectInputStream is, ObjectOutputStream os, WorkingThread workingThread, Boolean isClient, List<Manager> managerList) {
		this.InputStream = is;
		this.OutputStream = os;
		this.workingThread = workingThread;
		this.isClient = isClient;
		this.clientlist = managerList;
	}

	public void write(NetMessage netMessage) {
		try {
			if (isClient)
			{
				OutputStream.writeUnshared(netMessage); 
			}
			else // Server -> Send Message to all clients  -- OK
			{ 
				for (Manager ma : clientlist) {
					ObjectOutputStream tempstream = ma.getOutputStream();
					tempstream.writeUnshared(netMessage);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ObjectOutputStream getOutputStream() {
		return OutputStream;
	}

	// Listen For Messages in own Thread
	@Override
	public void run() {
		while (true) {
			try {
				Object n = InputStream.readObject();
				workingThread.add((NetMessage) n);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
