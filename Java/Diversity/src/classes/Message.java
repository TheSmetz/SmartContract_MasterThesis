package classes;

public class Message {
    private MessageContent payload;
    private MessageType messageType;

    public Message(MessageType messageType, MessageContent payload) {
        this.messageType = messageType;
        this.payload = payload;
    }

    // @Override
    // public String toString() {
    //     return "{" +
    //         ", messageType='" + getmessageType() + "'" +
    //         ", payload='" + getPayload() + "'" +
    //         "}";
    // }

    public MessageType getmessageType() {
        return this.messageType;
    }

    public void setmessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageContent getPayload() {
        return this.payload;
    }

    public void setPayload(MessageContent payload) {
        this.payload = payload;
    }

    
}
