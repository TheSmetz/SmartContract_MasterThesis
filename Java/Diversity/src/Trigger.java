import java.lang.reflect.Type;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Templates;

import com.google.gson.reflect.TypeToken;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import classes.*;
import encrypt.ECC;
import encrypt.JSONConverter;

import org.msgpack.*;

public class Trigger {
    public static void main(String[] args) throws Exception {

        // Security.addProvider(new BouncyCastleProvider());
        // ECC.generateKeyPair();

        // Client c = new Client(4444, "127.0.0.1");
        // c.runClient();
        // InitMessage initMessage = new InitMessage();
        // Type msgType = new TypeToken<Message<InitMessage>>() {}.getType();
        // Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT, initMessage);

        // String msg = JSONConverter.toJSON(m, msgType);
        // c.sendMessage(msg);

        InitMessage initMessage = new InitMessage();
        Type msgType = new TypeToken<Message<InitMessage>>() {}.getType();
        Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT, initMessage);

        MessagePack msgpack = new MessagePack();
        // Serialize
        byte[] raw = msgpack.write(m);

        Message<InitMessage> m_received = msgpack.read(raw);

    }
}