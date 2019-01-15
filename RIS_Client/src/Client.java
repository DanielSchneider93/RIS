import java.io.*;
import java.net.*;
import java.util.*;

import common.GenerateWorld;
import common.KI;
import common.Manager;
import common.MapCache;
import common.WorkingThread;
import common.World;
import common.WorldSegment;

public class Client {
	private final int port = 9090;
	private final String host = "localhost";

	private Socket socket;
	private WorkingThread workingThread;
	private Manager manager;
	private List<Manager> ManagerList;
	private World world;
	private UpdateGraphic ug;
	private MapCache mapCache;
	private KI ki;

	public static void main(String[] args) throws UnknownHostException, IOException {
		@SuppressWarnings("unused")
		Client c = new Client();
	}

	public Client() throws UnknownHostException, IOException {
		System.out.println("Start Client ....");
		ManagerList = new ArrayList<>();
		socket = new Socket(host, port);
		world = new World(ManagerList);

		System.out.println("Starting Working Thread ....");
		workingThread = new WorkingThread(world, false); // false -> is Client
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

		System.out.println("Starting KI ....");
		ki = new KI(world);
		Thread ki_t = new Thread(ki);
		ki_t.setDaemon(true);
		ki_t.start();

		System.out.println("Starting Graphic ....");
		Graphic graphic = new Graphic(world, gw);
		ug = graphic.getUpdategraphic();

		System.out.println("Starting Event Queue ....");
		EventQueueThread q = new EventQueueThread(ug, world);
		Thread eventQueueThread = new Thread(q);
		eventQueueThread.setDaemon(true);
		eventQueueThread.start();

		System.out.println("Done!");
	}
}
