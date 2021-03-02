package classes.MessageContents;

import java.util.Objects;

import classes.AC;
import classes.MessageContent;

public class ACMessage extends MessageContent {

    private AC content;
    private int id;


    public ACMessage(int id) {
        this.generate();
        this.id = id;
    }

    public void generate() {
        this.content = new AC();
        this.content.init();
    }

    public int getId() {
        return this.id;
    }

    public boolean verifyPOC(POCSigned pocSigned){
        int hashNounce = this.content.getNounce().hashCode();
        int hashRes = Double.hashCode(this.content.getResult());
        long hash = Double.hashCode(this.content.getResult()) * this.content.getNounce().hashCode() * this.id;
        if(hash==pocSigned.getPocContent().getFirstPart()){
            return true;
        }else{
            return false;
        }
    }
}
