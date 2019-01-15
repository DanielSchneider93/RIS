package common;

public class IDMessage implements NetMessage {
   private Integer message;
    
    public IDMessage(Integer msg) {
        this.message = msg;
    }
    
    public Integer getMsg() {
		return message;
	}
}
