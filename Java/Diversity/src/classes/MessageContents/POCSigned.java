package classes.MessageContents;

import java.util.List;

import classes.MessageContent;
import classes.POC;

public class POCSigned extends MessageContent {
    private POC pocContent;
    private byte[] signedMessage;

    public POCSigned() {
    }

    public POC getPocContent() {
        return this.pocContent;
    }

    public void setPocContent(POC pocContent) {
        this.pocContent = pocContent;
    }

    public byte[] getSignedMessage() {
        return this.signedMessage;
    }

    public void setSignedMessage(byte[] signedMessage) {
        this.signedMessage = signedMessage;
    }

    public static int consensus(List<POCSigned> pocSigneds){
        int hashResult = pocSigneds.get(0).getPocContent().getHashResult();
        for (int i = 1; i < pocSigneds.size(); i++) {
            if(pocSigneds.get(i).getPocContent().getHashResult()!=hashResult){
                return pocSigneds.get(i).getPocContent().getId();
            }
        }
        return -99;
    }

    public int getId() {
        return this.pocContent.getId();
    }

}
