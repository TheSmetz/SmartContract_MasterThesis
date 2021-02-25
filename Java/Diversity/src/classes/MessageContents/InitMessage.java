package classes.MessageContents;

import classes.Contract;
import classes.MessageContent;

public class InitMessage extends MessageContent {
    
    private Contract contract;

    public InitMessage(){
        this.contract = new Contract();
    }

    public Contract getContract(){
        this.contract = new Contract();
        return this.contract;
    }
}
