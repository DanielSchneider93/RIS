package common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkingThread implements Runnable {
	BlockingQueue<NetMessage> messages;
	World world;
	boolean isServer;

	private Map<Class<? extends NetMessage>, NetMessageInterface<? extends NetMessage>> netMessageHandlerMap;

	public WorkingThread(World world, boolean isServer) {
		this.world = world;
		this.isServer = isServer;
		netMessageHandlerMap = new HashMap<>();
		netMessageHandlerMap.put(IDMessage.class, new IDMessageHandler(world));
		netMessageHandlerMap.put(PosMessage.class, new PosMessageHandler(world));
		messages = new LinkedBlockingQueue<>();
	}

	public void add(NetMessage netMessage) {
		messages.offer(netMessage);
	}

	@Override
	public void run() {
		while(true) {
			try {
				NetMessage n = messages.take();
				netMessageHandlerMap.get(n.getClass()).handle(n, isServer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
