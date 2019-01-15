package common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Manager implements Runnable {

	private final ObjectInputStream InputStream;
	private final ObjectOutputStream OutputStream;
	private WorkingThread workingThread;

	public Manager(ObjectInputStream is, ObjectOutputStream os, WorkingThread workingThread) {
		this.InputStream = is;
		this.OutputStream = os;
		this.workingThread = workingThread;
	}

	public void write(NetMessage netMessage) {
		try {
			OutputStream.writeUnshared(netMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeID(NetMessage netMessage) throws IOException {
		OutputStream.writeUnshared(netMessage);
	}

	public ObjectOutputStream getOutputStream() {
		return OutputStream;
	}

	// Listen For Messages
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
