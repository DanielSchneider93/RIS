package common;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Vector;

//Server Manger
public class Manager implements Runnable
{ 

	private final InputStream InputStream; 
	private final OutputStream OutputStream;
	private WorkingThread workingThread; 
	private boolean isClient; // true = client , false = server
	private List<Manager> clientlist;


	public Manager(InputStream is, OutputStream os, WorkingThread workingThread, Boolean isClient, List<Manager> managerList) { 
		this.InputStream = is; 
		this.OutputStream = os; 
		this.workingThread = workingThread;
		this.isClient = isClient;
		this.clientlist = managerList;
	} 

	public void write(NetMessage netMessage) {
		try {
			ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(OutputStream);
			if (isClient)
			{
				ObjectOutputStream.writeUnshared(netMessage); 
			}
			else // Server -> so Send Message to all clients 
			{ 
			

					for (Manager ma : clientlist) {
					ObjectOutputStream ObjectOutputStreamTemp = new ObjectOutputStream(ma.OutputStream);
					ObjectOutputStreamTemp.writeObject(netMessage);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Listen For Messages in own Thread
	@Override
	public void run() { 
		while (true) { 
			try {
				ObjectInputStream ObjectInputStream = new ObjectInputStream(InputStream);
				NetMessage n = (NetMessage)ObjectInputStream.readObject();
				workingThread.add(n);
				System.out.println(n);
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}  
	}
}

