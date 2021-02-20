package classes;

public class GenericMessage {
    private MessageType messageType;

    public GenericMessage(){}

    public MessageType getmessageType() {
        return this.messageType;
    }

    public void setmessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
