package classes.MessageContents;

import java.security.PublicKey;

import classes.AC;
import classes.MessageContent;
import encrypt.ECC;
import encrypt.JSONConverter;

public class ACMessage extends MessageContent {

    private AC content;
    private byte[] publicKeySender;
    private byte[] signedMessage;
    private byte[] prevContent;



    public ACMessage(byte[] publicKeySender, byte[] signedMessage, AC content) {
        this.publicKeySender = publicKeySender;
        this.signedMessage = signedMessage;
        this.content = content;
    }

    public void generate() {
        this.content = new AC();
        this.content.init();
        this.signedMessage = ECC.encrypt(JSONConverter.toJSON(this.content, AC.class));
        this.publicKeySender = ECC.getPublicKey().getEncoded(); //TODO: not sure
    }

    public boolean verify() {
        PublicKey pk = ECC.getPublicKeyFromBytes(publicKeySender);
        if(prevContent!=null){
            return ECC.decrypt(pk, this.signedMessage, JSONConverter.toJSON(this.prevContent));
        }else{
            return ECC.decrypt(pk, this.signedMessage, JSONConverter.toJSON(content, AC.class));
        }
    }
}
