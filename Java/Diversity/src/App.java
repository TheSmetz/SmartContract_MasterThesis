import java.security.Security;

import org.bouncycastle.jce.provider.*;
import classes.*;
import encrypt.ECC;

public class App {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Server s = new Server(4444);
        s.setClient(3333, "10.0.1.244");
        s.runServer();
    }
}
