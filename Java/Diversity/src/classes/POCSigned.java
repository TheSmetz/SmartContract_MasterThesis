package classes;

import java.util.List;

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
        byte[] hashResult = pocSigneds.get(0).getPocContent().getHashResult();
        System.out.println("HashResult: "+hashResult);
        for (int i = 1; i < pocSigneds.size(); i++) {
            System.out.println(pocSigneds.get(i).getPocContent().getHashResult());
            if(!pocSigneds.get(i).getPocContent().getHashResult().equals(hashResult)){
                return pocSigneds.get(i).getPocContent().getId();
            }
        }
        return -99;
    }


}
