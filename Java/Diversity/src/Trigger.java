import java.lang.reflect.Type;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import com.google.gson.reflect.TypeToken;

import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import classes.*;
import encrypt.ECC;
import encrypt.JSONConverter;

public class Trigger {
    public static void main(String[] args) throws Exception {

        Security.addProvider(new BouncyCastleProvider());
        ECC.generateKeyPair();

        Client c = new Client(4444, "127.0.0.1");

        InitMessage initMessage = new InitMessage();
        Type msgType = new TypeToken<Message<InitMessage>>() {}.getType();
        Message<InitMessage> m = new Message<InitMessage>(MessageType.INIT, initMessage);

        String msg = JSONConverter.toJSON(m, msgType);
        GenericMessage mmm = JSONConverter.toObject(msg, GenericMessage.class);
        Message<InitMessage> mm = JSONConverter.toObject(msg, msgType);


        Integer[] w = {1,2,3};
        POC p = new POC(1, w, 3, new Contract());
        POCMessage poc = new POCMessage(ECC.getPublicKey().getEncoded(),ECC.encrypt(JSONConverter.toJSON(p)) ,p);
        Type msgType2 = new TypeToken<Message<POCMessage>>() {}.getType();
        Message<POCMessage> m2 = new Message<POCMessage>(MessageType.PoC, poc);

        String msg2 = JSONConverter.toJSON(m2, msgType2);
        GenericMessage mmmm = JSONConverter.toObject(msg2, GenericMessage.class);
        Message<POCMessage> mmmmmm = JSONConverter.toObject(msg2, msgType2);

        if(mmmmmm.getmessageContent() instanceof POCMessage){
            POCMessage content = mmmmmm.getmessageContent();
            System.out.println("It's valid: "+content.verify());
        }


        

    }
}