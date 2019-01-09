import java.io.*;
import java.net.*;
import java.util.*;

import common.GenerateWorld;
import common.Manager;
import common.MapCache;
import common.WorkingThread;
import common.World;
import common.WorldSegment;

public class Client {
	final int port = 9090;
	final String host = "localhost";

	WorkingThread workingThread;
	Manager manager;
	List<Manager> ManagerList;
	Object playerpos;
	World world;
	UpdateGraphic ug;
	MapCache mapCache;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client c = new Client();
	}

	public Client() throws UnknownHostException, IOException {
		System.out.println("Start Client ....");
		ManagerList = new ArrayList<>();
		Socket socket = new Socket(host, port);
		World world = new World(ManagerList);

		System.out.println("Starting Working Thread ....");
		workingThread = new WorkingThread(world, false); // false -> is not Server
		Thread workingT = new Thread(workingThread);
		workingT.setDaemon(true);
		workingT.start();
		
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

		System.out.println("Starting Manager Thread ....");
		manager = new Manager(inputStream, outputStream, workingThread);
		Thread thread = new Thread(manager); 
		thread.setDaemon(true);
		thread.start();

		ManagerList.add(manager);
		
		System.out.println("Generating Map ....");
		GenerateWorld gw = new GenerateWorld();
		ArrayList<WorldSegment> segmentList = gw.generateMap();
		world.setSegmentList(segmentList);
		
		System.out.println("Starting Map Cache ....");
		mapCache = new MapCache(world);
		Thread mapThread = new Thread(mapCache); 
		mapThread.setDaemon(true);
		mapThread.start();

		System.out.println("Starting Graphic ....");
		Graphic graphic = new Graphic(world, mapCache);
		ug = graphic.getUpdategraphic();
		
		System.out.println("Starting Event Queue ....");
		EventQueueThread q = new EventQueueThread(ug, world);
		Thread eventQueueThread = new Thread(q);
		eventQueueThread.setDaemon(true);
		eventQueueThread.start();
		
		System.out.println("Done!");
	}
}
