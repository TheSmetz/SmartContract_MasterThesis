import java.security.Security;

import org.bouncycastle.jce.provider.*;
import classes.*;
import encrypt.ECC;

public class App {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Server s = new Server(4444);
        s.setClient(4444, "10.0.2.51");
        s.runServer();
    }
}