package common;
public class PosMessage implements NetMessage {
    private Object msg;
    
    public PosMessage(GameObject objectToSend) {
        this.msg = objectToSend;
    }

	public Object getMsg() {
		return msg;
	}
}
