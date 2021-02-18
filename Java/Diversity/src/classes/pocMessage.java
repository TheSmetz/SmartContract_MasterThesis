package classes;

import java.security.PublicKey;
import encrypt.ECC;
import encrypt.JSONConverter;

public class POCMessage implements MessageContent{
    
    private PublicKey publicKeySender;
    private byte[] signedMessage;
    private POC content;

    public POCMessage(PublicKey publicKeySender, byte[] signedMessage, POC content){
        this.publicKeySender = publicKeySender;
        this.signedMessage = signedMessage;
        this.content = content;
    }

    public boolean verify(){
        return ECC.verify(this.publicKeySender, this.signedMessage, JSONConverter.toJSON(content));
    }
}