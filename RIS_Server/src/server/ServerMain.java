package server;

import java.io.*;
import java.net.*;
import java.util.*;

import common.IDMessage;
import common.Manager;
import common.GameObject;
import common.PosMessage;
import common.UpdateWorld;
import common.Vectorlist;
import common.WorkingThread;
import common.World;


public class ServerMain {

	static int port = 9091;
	List<Manager> ManagerList;
	WorkingThread workingThread;
	World world;
	private int playerID = 1;
	int playerHitBox = 100;
	boolean isEatable = false; //for player

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
		

		ServerSocket listener = new ServerSocket(port);
		System.out.println("Server waiting for connections on Port 9090");

		while (true)  
		{ 
			Socket clientSocket = listener.accept();
			System.out.println("Connected to " +listener.getInetAddress()+ " on port " +listener.getLocalPort());
			
			ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
			ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
			
			Manager connectionManager = new Manager(inputStream, outputStream, workingThread, ManagerList);
			Thread thread = new Thread(connectionManager); 
			thread.start();
			
			System.out.println("Created Server Manager for Client " + playerID);
			
			//Create Player, Add to World with ID and send ID to the new Client to let him know what his player is
			GameObject player = new GameObject(playerID, 400, 400, playerHitBox, isEatable);
			world.addObjectToWorld(player);
			ManagerList.add(connectionManager); 

			IDMessage msg = new IDMessage(playerID);
			connectionManager.writeID(msg);
						
			UpdateWorld w = world.getUpdateWorld();
			w.sendClientThePlayer(player, connectionManager);
			
			System.out.println("Added Player: " + playerID +  " to World");
			playerID++;
		} 
	}
}
