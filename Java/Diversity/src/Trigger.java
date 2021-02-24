import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.security.Security;

import com.google.gson.reflect.TypeToken;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.msgpack.unpacker.Unpacker;

import classes.*;
import encrypt.ECC;
import encrypt.JSONConverter;
import encrypt.MSGpack;

public class Trigger {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Client c = new Client(4444, "127.0.0.1");
        c.runClient();
        InitMessage initMessage = new InitMessage();
        Type msgType = new TypeToken<Message<InitMessage>>() {
        }.getType();
        Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT, initMessage);

        // String msg = JSONConverter.toJSON(m, msgType);
        // c.sendMessage(msg);
        // Client c2 = new Client(3333, "127.0.0.1");
        // c2.runClient();
        // c2.sendMessage(msg);

        
        POCMessage pocMsg = new POCMessage();
        Integer[] w = { 1, 2, 3 };
        pocMsg.generate(4444, w, 3, new Contract());
        Message<POCMessage> msg = new Message<POCMessage>(MessageType.PoC, pocMsg);
        System.out.println("msg " + msg.getmessageContent().getContent().getHashResult());
        MessagePack msgPack = new MessagePack();
        msgPack.register(Contract.class);
        msgPack.register(POC.class);
        msgPack.register(POCMessage.class);
        msgPack.register(MessageType.class);
        msgPack.register(Message.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = msgPack.createPacker(out);
        packer.write(msg);
        byte[] bytes = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Unpacker unpacker = msgPack.createUnpacker(in);
        Message<POCMessage> msg2 = unpacker.read(Message.class);

    }
}