package common;

import java.io.IOException;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

import classes.Message;
import classes.MessageContent;

public class MessageTemplate extends AbstractTemplate<Message> {
    public MessageTemplate() {
    }

    public void write(Packer pk, Message target, boolean required) throws IOException {
        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write(target);
    }

    public Message read(Unpacker u, Message to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }
        return u.readString();
    }

    static public StringTemplate getInstance() {
        return instance;
    }

    static final StringTemplate instance = new StringTemplate();

}
