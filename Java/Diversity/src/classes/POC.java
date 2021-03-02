package classes;

import java.util.Arrays;
import java.util.Objects;

import encrypt.ECC;
import encrypt.JSONConverter;
import storage.LocalStorage;

public class POC {
    private long firstPart;
    private Contract aContract;
    private int t;
    private int hashResult;
    private int id;

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

    public long getFirstPart() {
        return this.firstPart;
    }

    public void init() {
        this.firstPart = this.generateFirstPart();
        this.hashResult = this.generateHashResult();
    }

    private long generateFirstPart() {
        LocalStorage.writeFile("nounce", ECC.nonceGenerator());
        LocalStorage.writeFile("res", aContract.getFunction()
                .apply(JSONConverter.toObject(LocalStorage.readFromFileByKey("window"), Integer[].class)).toString());
        long resHash = Double.hashCode(Double.parseDouble(LocalStorage.readFromFileByKey("res")));
        long nounceHash = LocalStorage.readFromFileByKey("nounce").hashCode();
        return resHash * nounceHash * (long) this.id;
    }

    private int generateHashResult() {
        Integer[] window = JSONConverter.toObject(LocalStorage.readFromFileByKey("window"), Integer[].class);
        return Arrays.hashCode(window);
    }
}