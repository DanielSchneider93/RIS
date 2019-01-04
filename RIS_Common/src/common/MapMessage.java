package common;

public class MapMessage implements NetMessage {
	Object map;
    
    public MapMessage(Object map) {
        this.map = map;
    }

	public Object getMsg() {
		return map;
	}
}
