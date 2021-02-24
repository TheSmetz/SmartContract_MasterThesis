package classes;

import common.Ansi;
import encrypt.ECC;

public class POC {

    private byte[] firstPart;
    private Contract aContract;
    private int t;
    private byte[] hashResult;

    private int id;
    private Integer[] w;
    private String nounce; // S(t,i)

    public POC(int id, Integer[] w, int t, Contract aContract) {
        this.id = id;
        this.w = w;
        this.t = t;
        this.aContract = aContract;
    }

    public int getId(){
        return this.id;
    }

    public byte[] getHashResult(){
        return this.hashResult;
    }

    public void init() {
        this.firstPart = this.generateFirstPart();
        this.hashResult = this.generateHashResult();
        System.out.println(Ansi.ANSI_YELLOW+this.hashResult+Ansi.ANSI_RESET);
    }

    private byte[] generateFirstPart() {
        double f = this.aContract.getFunction().apply(this.w);
        int idPOC = this.id;
        this.nounce = ECC.nonceGenerator();
        String n = this.nounce;
        Object myobj = new Object() {
            double res = 1.0;
            String nounce = n;
            int id = idPOC;
        };
        return ECC.hash(myobj);
    }

    private byte[] generateHashResult() {
        return ECC.hash(this.aContract.getFunction().apply(this.w));
    }
}