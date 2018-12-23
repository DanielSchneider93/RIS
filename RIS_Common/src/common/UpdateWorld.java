package common;

import java.util.LinkedList;
import java.util.List;

public class UpdateWorld {

	List<Manager> managerList;
	LinkedList<Object> world;
	Player player = new Player(0);
	Class pClass;

	public UpdateWorld(LinkedList<Object> world, List<Manager> managerList) {
		this.world = world;
		this.managerList = managerList;
		pClass = player.getClass();

	}

	public void sendUpdatedWorld(Player p) {
		PosMessage msg = new PosMessage(p);
		for (Manager m : managerList) {
			m.write(msg);
		}
	}
	
	public void sendClientThePlayer(Player p, Manager connectionManager) {
		PosMessage msg = new PosMessage(p);
		connectionManager.write(msg);	
	}

	public void shareWorldWithClients() {
		for (Object o : world) {
			Class oClass = o.getClass();
			if (oClass == pClass) {
				Player tempPlayer = (Player) o;
				PosMessage msg = new PosMessage(tempPlayer);
				for (Manager m : managerList) {
					m.write(msg);
				}
			}
		}
	}

}
