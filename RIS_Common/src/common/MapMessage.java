package common;

public class MapMessage implements NetMessage {
	private Object map;
    
    public MapMessage(Object map) {
        this.map = map;
    }

	public Object getMsg() {
		return map;
	}
}
