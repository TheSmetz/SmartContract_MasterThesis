package classes;

import encrypt.ECC;

//TODO W at time t
//S = nounce (fresh random secret)
//Message = Hash(f(W[t]) || S(t,i) || IDi) || Contratto || t || H(W[t])
public class pocMessage implements MessageContent{
    private int id;
    private Integer[] w;
    private int t;      //time
    private String nounce; // S(t,i)
    private Contract aContract;

    public pocMessage(int id, Integer[] w, int t, Contract aContract) {
        this.id = id;
        this.w = w;
        this.t = t;
        this.aContract = aContract;
    }

    public String generatePoCMessage(){
        double f = this.aContract.getFunction().apply(this.w);
        this.nounce = ECC.nonceGenerator();
        byte[] firstPart = ECC.hashString(f + " " + this.nounce + " " + this.id);
        String secondPart = this.aContract.getContract() + " " + this.t + " " + this.w.hashCode();
        return firstPart.toString() + " " + secondPart;
    }
}
