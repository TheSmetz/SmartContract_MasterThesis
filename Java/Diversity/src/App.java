import java.security.Security;

import classes.Server;
import encrypt.ECC;

public class App {

    public static void main(String[] args) throws Exception {
        // Server s = new Server(5555);
        // s.setClient(5555, "10.0.2.208");
        // s.runServer();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        ECC.generateKeyPair();
        byte[] signature = ECC.encrypt("CIAO");
        System.out.println(signature);
        boolean res = ECC.verify(ECC.getPublicKey(), signature, "CIAO");
        System.out.println(res);
    }
}
