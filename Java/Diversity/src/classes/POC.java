package classes;

import encrypt.ECC;

public class POC {

    private byte[] firstPart;
    private Contract aContract;
    private int t;
    private byte[] secondPart;

    private int id;
    private Integer[] w;
    private String nounce; // S(t,i)

    public POC(int id, Integer[] w, int t, Contract aContract) {
        this.id = id;
        this.w = w;
        this.t = t;
        this.aContract = aContract;
    }

    public void init() {
        this.firstPart = this.generateFirstPart();
        this.secondPart = this.generateSecondPart();
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

    private byte[] generateSecondPart() {
        Integer[] window = this.w;
        Object myobj = new Object() {
            Integer[] w = window;
        };
        return ECC.hash(myobj);
    }
}