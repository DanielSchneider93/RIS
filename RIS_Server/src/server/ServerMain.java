package server;

import java.io.*;
import java.net.*;
import java.util.*;

import common.IDMessage;
import common.Manager;
import common.GameObject;
import common.GenerateWorld;
import common.UpdateWorld;
import common.WorkingThread;
import common.World;
import common.WorldSegment;

public class ServerMain {

	static int port = 9090;
	List<Manager> ManagerList;
	WorkingThread workingThread;
	World world;
	private int playerID = 1;
	int playerHitBox = 100;
	boolean isEatable = false; // for player

	public static void main(String[] args) throws IOException {
		new ServerMain();
		System.out.println("Started Server");
	}

	public ServerMain() throws IOException {
		System.out.println("Created Clientlist");
		ManagerList = new ArrayList<>();

		System.out.println("Created Server World");
		world = new World(ManagerList);

		System.out.println("Created Working Thread");
		workingThread = new WorkingThread(world, true); // true -> isServer
		Thread t = new Thread(workingThread);
		t.setDaemon(true);
		t.start();

		GenerateWorld gw = new GenerateWorld();
		ArrayList<WorldSegment> segmentList = gw.generateMap();
		world.setSegmentList(segmentList);

		ServerSocket listener = new ServerSocket(port);
		System.out.println("Server waiting for connections on Port 9090");

		while (true) {
			Socket clientSocket = listener.accept();
			System.out.println("Connected to " + listener.getInetAddress() + " on port " + listener.getLocalPort());

			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

			Manager connectionManager = new Manager(inputStream, outputStream, workingThread);
			Thread thread = new Thread(connectionManager);
			thread.setDaemon(true);
			thread.start();

			System.out.println("Created Server Manager for Client " + playerID);

			// Create Player, Add to World with ID and send ID to the new Client to let him
			// know what his player is
			GameObject player = new GameObject(playerID, 400, 400, playerHitBox, isEatable, 10);
			world.addObjectToWorld(player);

			if (world.getEnemy() == null) {
				GameObject enemy = new GameObject(50, 600, 600, playerHitBox, isEatable, 100);
				world.addObjectToWorld(enemy);
			}

			ManagerList.add(connectionManager);

			IDMessage msg = new IDMessage(playerID);
			connectionManager.writeID(msg);

			UpdateWorld w = world.getUpdateWorld();
			w.sendClientThePlayer(player, connectionManager);

			System.out.println("Added Player: " + playerID + " to World");
			playerID++;
		}
	}
}
