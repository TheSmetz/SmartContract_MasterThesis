import java.security.Security;

import org.bouncycastle.jce.provider.*;
import classes.*;
import encrypt.ECC;

public class App2 {
    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Server s = new Server(3333);
        s.runServer();
    }
}