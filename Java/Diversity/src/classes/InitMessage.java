package classes;

public class InitMessage implements MessageContent{
    
    private Contract contract;

    public InitMessage(){}

    public Contract getContract(){
        return this.contract;
    }
}
