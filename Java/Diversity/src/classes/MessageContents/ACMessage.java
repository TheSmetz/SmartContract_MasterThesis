package classes.MessageContents;

import classes.MessageContent;

public class ACMessage extends MessageContent {

    public Double result;
    public String nounce;


    public ACMessage() {
    }

    public ACMessage(Double result, String nounce){
        this.result = result;
        this.nounce = nounce;
    }


}
