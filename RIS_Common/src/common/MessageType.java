package common;
public enum MessageType {
	
    CHAT((byte)0), POS((byte)1);
	
    private byte b;
    
    //Getter and Setter
    MessageType(byte b) {
        this.b = b;
    }

    public byte getID() {
        return b;
    }

    public static MessageType getMessageType(byte b) {
        switch(b) {
            case 0:
                return CHAT;
            case 1:
            	return POS;
            	
        }
        throw new IllegalArgumentException("Enum with index " + b + " does not exist.");
    }
}