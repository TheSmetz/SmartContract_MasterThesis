import java.security.Security;

import classes.*;
import encrypt.ECC;

public class App {
    public static void main(String[] args) throws Exception {
        // Server s = new Server(5555);
        // s.setClient(5555, "10.0.2.208");
        // s.runServer();

        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // ECC.generateKeyPair();
        // byte[] signature = ECC.encrypt("CIAO");
        // System.out.println(signature);
        // boolean res = ECC.verify(ECC.getPublicKey(), signature, "CIAO");
        // System.out.println(res);

        Message msg = new Message(MessageType.INIT,new MessageContent(){});

        String s = JSONConverter.toJSON(msg);
        System.out.println(s);
        Message m = JSONConverter.toObject(s, Message.class);
        System.out.println(m.toString());



        // String json = Message.StringToJson(msg);
        // System.out.println(Message.JsonToString(json));
        // Contract c = new Contract();
        // Integer[] v = {1,2,3};
        // pocMessage p = new pocMessage(1, v, 3, c);
        // System.out.println(p.generatePoCMessage());
    }
}