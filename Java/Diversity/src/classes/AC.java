package classes;

public class AC {

    private String nounce; // S(t,i)
    private Double res;

    public AC(){

    }

    public AC(String nounce, Double res){
        this.nounce = nounce;
        this.res = res;
    }

    public String getNounce(){
        return this.nounce;
    }

    public Double getResult(){
        return this.res;
    }
    
}
