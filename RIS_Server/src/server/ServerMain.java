package server;

import java.io.*;
import java.net.*;
import java.util.*;

import common.Manager;
import common.Player;
import common.Vectorlist;
import common.WorkingThread;
import common.World;


public class ServerMain {

	static int port = 9090;
	List<Manager> ManagerList;
	WorkingThread workingThread;
	World world;
	//First Player = 1, then 2...
	private int playerID = 1;

	public static void main(String[] args) throws IOException {
		ServerMain server = new ServerMain();
		
	}

	public ServerMain() throws IOException {
		ManagerList = new ArrayList<>();
		
		//Create World Object
		world = new World(ManagerList, null);
new Thread(world).start();
		
		//Create Working Thread in new Thread -> List of Messages, distribute to the registered Handlers
		workingThread = new WorkingThread(world);
		Thread w = new Thread(workingThread);
		w.setDaemon(true);
		w.start();

		//Create Socket
		ServerSocket listener = new ServerSocket(port);
		System.out.println("Server waiting for connections on Port 9090");

		//Server Connect Loop
		while (true)  
		{ 
			// accept the client
			Socket clientSocket = listener.accept();
			System.out.println("Connected to " +listener.getInetAddress()+ " on port " +listener.getLocalPort());

			//create manager for every Client that Connects and Create Thread for each
			Manager handlerThread = new Manager(clientSocket.getInputStream(), clientSocket.getOutputStream(), workingThread, false , ManagerList);
			Thread thread = new Thread(handlerThread); 
			thread.start();
			System.out.println("started Manager in Server for new Client");
			
			//Add new Player to World and give id IDs
			Player player = new Player(playerID);
			world.addObjectToWorld(player);

			//Add Thread of Manager to List of "Clients"
			ManagerList.add(handlerThread); 
			System.out.println("players: " + playerID);
			playerID++;
		} 
	}
}
