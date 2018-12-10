import java.io.*;
import java.net.*;
import java.util.*;

import common.Manager;
import common.WorkingThread;
import common.World;

public class Client {
	final int port = 9091;
	final String host = "localhost";

	WorkingThread workingThread;
	Manager manager;
	List<Manager> ManagerList;
	Object playerpos;
	World world;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client c = new Client();
	}

	public Client() throws UnknownHostException, IOException {
		ManagerList = new ArrayList<>();
		
		try (Socket socket = new Socket(host, port)) {
			World world = new World(ManagerList);
			Thread worldThread = new Thread(world);
			worldThread.setDaemon(true);
			worldThread.start();

			workingThread = new WorkingThread(world);
			Thread workingT = new Thread(workingThread);
			workingT.setDaemon(true);
			workingT.start();
			System.out.println("started Client Working Thread");
			
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			
			manager = new Manager(inputStream, outputStream, workingThread, true, ManagerList);
			Thread thread = new Thread(manager); 
			thread.setDaemon(true);
			thread.start();
			
			System.out.println("started Client Manager Thread");
					
			ManagerList.add(manager);

			Graphic graphic = new Graphic(world);
			UpdateGraphic ug = graphic.getUpdategraphic();
			
			EventQueueThread q = new EventQueueThread(ug);
			Thread eventQueueThread = new Thread(q);
			eventQueueThread.setDaemon(true);
			eventQueueThread.start();

			while (true) {
				Thread.yield();
				// System.out.println("write message to Server");
				// manager.write(new PosMessage(playerpos));
				// manager.write(new ChatMessage("Test"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
