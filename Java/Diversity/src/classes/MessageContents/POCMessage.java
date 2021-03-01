package classes.MessageContents;

import java.security.PublicKey;
import encrypt.ECC;
import encrypt.JSONConverter;
import storage.LocalStorage;
import classes.MessageContent;
import classes.POC;
import classes.Contract;

public class POCMessage extends MessageContent{
    
    private byte[] publicKeySender;
    private byte[] signedMessage;
    private POC content;
    private byte[] prevContent;

    public POCMessage() {}

    public boolean verify() {
        PublicKey pk = ECC.getPublicKeyFromBytes(publicKeySender);
        if(prevContent!=null){
            return ECC.decrypt(pk, this.signedMessage, JSONConverter.toJSON(this.prevContent));
        }else{
            return ECC.decrypt(pk, this.signedMessage, JSONConverter.toJSON(content, POC.class));
        }
    }

    public void generate(int id, int t, Contract aContract) {
        this.content = new POC(id, t, aContract);
        this.content.init();
        this.signedMessage = ECC.encrypt(JSONConverter.toJSON(this.content, POC.class));
        this.publicKeySender = ECC.getPublicKey().getEncoded();
    }

    public byte[] getSignedMessage(){
        return this.signedMessage;
    }

    public void setSignedMessage(byte[] sM){
        this.signedMessage = sM;
    }

    public POC getContent(){
        return this.content;
    }

    public byte[] getPrevContent(){
        return this.prevContent;
    }

    public void setPrevContent(byte[] pC){
        this.prevContent = pC;
    }

    public byte[] getPublicKeySender(){
        return this.publicKeySender;
    }

    public void setPublicKeySender(byte[] pKS){
        this.publicKeySender = pKS;
    }
}