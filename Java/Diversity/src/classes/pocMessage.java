package classes;

import java.security.PublicKey;
import encrypt.ECC;
import encrypt.JSONConverter;

public class POCMessage extends MessageContent{
    
    private byte[] publicKeySender;
    private byte[] signedMessage;
    private POC content;

    public POCMessage() {}

    public POCMessage(byte[] publicKeySender, byte[] signedMessage, POC content) {
        this.publicKeySender = publicKeySender;
        this.signedMessage = signedMessage;
        this.content = content;
    }

    public boolean verify() {
        PublicKey pk = ECC.getPublicKeyFromBytes(publicKeySender);
        return ECC.verify(pk, this.signedMessage, JSONConverter.toJSON(content, POC.class));
    }

    public void generate(int id, Integer[] w, int t, Contract aContract) {
        this.content = new POC(id, w, t, aContract);
        this.content.init();
        this.signedMessage = ECC.encrypt(JSONConverter.toJSON(this.content, POC.class));
        this.publicKeySender = ECC.getPublicKey().getEncoded();
    }
}