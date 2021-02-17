package classes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    private String author;
    private String message_type;
    private String payload;

    public Message(String auth, String msg_type, String payload) {
        this.author = auth;
        this.message_type = msg_type;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return "{" +
            " author='" + getAuthor() + "'" +
            ", message_type='" + getMessage_type() + "'" +
            ", payload='" + getPayload() + "'" +
            "}";
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage_type() {
        return this.message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getPayload() {
        return this.payload;
    }

    public void setPayload(String payload) {
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
