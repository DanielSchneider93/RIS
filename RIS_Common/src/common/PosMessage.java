package common;
public class PosMessage implements NetMessage {
    Object msg;
    
    public PosMessage(Object objectToSend) {
        this.msg = objectToSend;
    }

	public Object getMsg() {
		return msg;
	}
}
