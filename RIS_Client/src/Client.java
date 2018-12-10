import java.io.*;
import java.net.*;
import java.util.*;

import common.Manager;
import common.WorkingThread;
import common.World;

public class Client {
	final int port = 9090;
	final String host = "localhost";

	WorkingThread workingThread;
	Manager manager;
	Object playerpos;
	World world;

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client c = new Client();
	}

	public Client() throws UnknownHostException, IOException {
		try (Socket socket = new Socket(host, port)) {

			workingThread = new WorkingThread(world);
			new Thread(workingThread).start();
			manager = new Manager(socket.getInputStream(), socket.getOutputStream(), workingThread, true, null);
			new Thread(manager).start();
			World world = new World(null, manager);

			Graphic graphic = new Graphic(world);
			UpdateGraphic ug = graphic.getUpdategraphic();
			EventQueueThread q = new EventQueueThread(ug);

			// Client Loop
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
