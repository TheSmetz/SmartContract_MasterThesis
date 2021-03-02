import java.lang.reflect.Type;
import java.security.Security;
import com.google.gson.reflect.TypeToken;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import classes.*;
import encrypt.ECC;
import encrypt.JSONConverter;
import classes.MessageContents.*;

public class Trigger {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        InitMessage initMessage = new InitMessage();
        Type msgType = new TypeToken<Message<InitMessage>>() {
        }.getType();
        Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT,
        initMessage);

        String msg = JSONConverter.toJSON(m, msgType);

        Client c = new Client(4444, "127.0.0.1");
        c.runClient();
        c.sendMessage(msg);

        Client c2 = new Client(3333, "10.0.1.244");
        c2.runClient();
        c2.sendMessage(msg);
        
    }
}
