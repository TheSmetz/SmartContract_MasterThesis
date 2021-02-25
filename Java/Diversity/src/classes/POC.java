package classes;
import encrypt.ECC;

public class POC {
    private int firstPart;
    private Contract aContract;
    private int t;
    private int hashResult;
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

    public int getHashResult(){
        return this.hashResult;
    }

    public Contract getContract(){
        return this.aContract;
    }

    public void init() {
        this.firstPart = this.generateFirstPart();
        this.hashResult = this.generateHashResult();
    }

    private int generateFirstPart() {
        int idPOC = this.id;
        return new Object() {
            double res = aContract.getFunction().apply(w);
            String nounce = ECC.nonceGenerator();
            int id = idPOC;
        }.hashCode();
    }

    private int generateHashResult() {
        return this.aContract.getFunction().apply(this.w).hashCode();
    }
}