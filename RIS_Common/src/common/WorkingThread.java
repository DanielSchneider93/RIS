package common;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WorkingThread implements Runnable {
	BlockingQueue<NetMessage> messages;
	World world;

	private Map<Class<? extends NetMessage>, NetMessageInterface<? extends NetMessage>> netMessageHandlerMap;

	public WorkingThread(World world) {
		this.world = world;
		netMessageHandlerMap = new HashMap<>();

		netMessageHandlerMap.put(ChatMessage.class, new ChatMessageHandler());
		netMessageHandlerMap.put(PosMessage.class, new PosMessageHandler());

		messages = new LinkedBlockingQueue<>();
	}

	public void add(NetMessage netMessage) {
		messages.offer(netMessage);
	}

	//Thread that get Messages from Manager via add and handles them in the correct Handler.
	@Override
	public void run() {
		while(true) {
			try {
				NetMessage n = messages.take();
				netMessageHandlerMap.get(n.getClass()).handle(n, world);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
