package classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    private MessageContent payload;
    private MessageType message_type;

    public Message(MessageType msg_type, MessageContent payload) {
        this.message_type = msg_type;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "{" +
            ", message_type='" + getMessage_type() + "'" +
            ", payload='" + getPayload() + "'" +
            "}";
    }

    public MessageType getMessage_type() {
        return this.message_type;
    }

    public void setMessage_type(MessageType message_type) {
        this.message_type = message_type;
    }

    public MessageContent getPayload() {
        return this.payload;
    }

    public void setPayload(MessageContent payload) {
        this.payload = payload;
    }

    public static String StringToJson(Message msg) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String msgJson = gson.toJson(msg);
        return msgJson;
    }

    public static Message JsonToString(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Message msgObject = gson.fromJson(json, Message.class);
        return msgObject;
    }
}
