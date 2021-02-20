package classes;

public class Message<T extends MessageContent> {
    private MessageType messageType;
    private T messageContent;

    public Message(){
        
    }

    public Message(MessageType messageType, T messageContent) {
        this.messageType = messageType;
        this.messageContent = messageContent;
    }

    // @Override
    // public String toString() {
    //     return "{" +
    //         ", messageType='" + getmessageType() + "'" +
    //         ", messageContent='" + getmessageContent() + "'" +
    //         "}";
    // }

    public MessageType getmessageType() {
        return this.messageType;
    }

    public void setmessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public T getmessageContent() {
        return this.messageContent;
    }

    public void setmessageContent(T messageContent) {
        this.messageContent = messageContent;
    }
}
