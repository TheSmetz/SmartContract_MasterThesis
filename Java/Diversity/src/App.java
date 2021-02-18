import java.security.Security;

import classes.*;
import encrypt.ECC;
import encrypt.JSONConverter;

public class App {
    public static void main(String[] args) throws Exception {
        // Server s = new Server(5555);
        // s.setClient(5555, "10.0.2.208");
        // s.runServer();

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        Integer[] w = {1,2,3};
        POC p = new POC(1,w,3,new Contract());
        String input = JSONConverter.toJSON(p);
        p = new POC(2,w,3,new Contract());
        ECC.generateKeyPair();
        Message m = new Message(MessageType.PoC, new POCMessage(ECC.getPublicKey(), ECC.encrypt(input), p));

        switch (m.getmessageType()) {
            case INIT:
                System.out.println("INIT");
                break;
            
            case PoC:
                if(m.getPayload() instanceof POCMessage){
                    POCMessage content = (POCMessage) m.getPayload();
                    System.out.println(content.verify());
                }
                break;
            default:
                break;
        }
    }
}