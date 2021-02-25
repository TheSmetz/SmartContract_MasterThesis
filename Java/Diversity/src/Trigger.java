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
        Type msgType = new TypeToken<Message<InitMessage>>() {
        }.getType();
        Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT, initMessage);

        String msg = JSONConverter.toJSON(m, msgType);
        c.sendMessage(msg);

        Client c2 = new Client(3333, "127.0.0.1");
        c2.runClient();
        c2.sendMessage(msg);

        Client c3 = new Client(2222, "127.0.0.1");
        c3.runClient();
        c3.sendMessage(msg);
    }
}