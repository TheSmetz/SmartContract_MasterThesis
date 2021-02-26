package classes;

import encrypt.ECC;
import encrypt.JSONConverter;
import storage.LocalStorage;

public class POC {
    private int firstPart;
    private Contract aContract;
    private int t;
    private int hashResult;
    private int id;
    // private Integer[] w;

    public POC(int id, int t, Contract aContract) {
        this.id = id;
        this.t = t;
        this.aContract = aContract;
    }

    public int getId() {
        return this.id;
    }

    public int getHashResult() {
        return this.hashResult;
    }

    public Contract getContract() {
        return this.aContract;
    }

    public void init() {
        this.firstPart = this.generateFirstPart();
        this.hashResult = this.generateHashResult();
    }

    private int generateFirstPart() {
        int idPOC = this.id;
        LocalStorage.writeFile("nounce", ECC.nonceGenerator());
        LocalStorage.writeFile("res", aContract.getFunction()
                .apply(JSONConverter.toObject(LocalStorage.readFromFileByKey("window"), Integer[].class)).toString());
        return new Object() {
            double res = Double.parseDouble(LocalStorage.readFromFileByKey("res"));
            String nounce = LocalStorage.readFromFileByKey("nounce");
            int id = idPOC;
        }.hashCode();
    }

    private int generateHashResult() {
        return JSONConverter.toObject(LocalStorage.readFromFileByKey("window"), Integer[].class).hashCode();
    }
}