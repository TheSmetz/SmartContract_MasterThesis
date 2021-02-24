import java.lang.reflect.Type;
import java.security.Security;


import com.google.gson.reflect.TypeToken;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import classes.*;
import encrypt.ECC;
import encrypt.JSONConverter;


public class Trigger {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Client c = new Client(4444, "127.0.0.1");
        c.runClient();
        InitMessage initMessage = new InitMessage();
        Type msgType = new TypeToken<Message<InitMessage>>() {}.getType();
        Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT, initMessage);

        String msg = JSONConverter.toJSON(m, msgType);
        c.sendMessage(msg);
    
    }
}