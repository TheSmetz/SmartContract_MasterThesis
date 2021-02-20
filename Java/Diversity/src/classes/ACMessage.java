package classes;

public class ACMessage extends MessageContent{
    public int t;      //time
    public Integer[] w;
    public String nounce; // S(t,i)


    public ACMessage(int t, Integer[] w) {
        this.t = t;
        this.w = w;
    }

    public String generateACMessage(){
        //TODO
        return "";
    }

}
