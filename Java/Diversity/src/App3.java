import java.security.Security;

import org.bouncycastle.jce.provider.*;
import classes.*;
import encrypt.ECC;

public class App3 {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Server s = new Server(2222);
        s.setClient(4444, "127.0.0.1");
        s.runServer();
    }
}