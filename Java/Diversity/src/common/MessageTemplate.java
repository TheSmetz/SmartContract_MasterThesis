package common;

import java.io.IOException;

import com.google.gson.reflect.TypeToken;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import classes.Message;
import classes.MessageContent;
import encrypt.JSONConverter;

public class MessageTemplate<T extends MessageContent> extends AbstractTemplate<Message<T>> {

    public MessageTemplate() {
    }

    public void write(Packer pk, Message<T> target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write(target);
    }

    public Message<T> read(Unpacker u, Message<T> to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }
        return JSONConverter.toObject(u.readString(), new TypeToken<Message<T>>() {}.getType());
    }

    public static <T extends MessageContent> MessageTemplate<T> getInstance() {
        return new MessageTemplate<T>();
    }

    

}
