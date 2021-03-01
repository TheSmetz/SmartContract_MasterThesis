package classes;

import java.util.Arrays;

import storage.LocalStorage;

public class AC {

    private String nounce; // S(t,i)
    private Double res;
    private Integer[] w;

    public AC() {
    }

    public void init() {
        this.nounce = LocalStorage.readFromFileByKey("nounce");
        this.res = Double.parseDouble(LocalStorage.readFromFileByKey("result"));
        getWindowFromFile();
    }

    // cast from STRING to INTEGER[]
    private void getWindowFromFile() {
        String str = LocalStorage.readFromFileByKey("window");
        int[] arr = Arrays.stream(str.substring(1, str.length() - 1).split(",")).map(String::trim)
                .mapToInt(Integer::parseInt).toArray();
        this.w = Arrays.stream(arr).boxed().toArray(Integer[]::new);
    }

    public String getNounce() {
        return this.nounce;
    }

    public Double getResult() {
        return this.res;
    }
    
    public Integer[] getWindow(){
        return this.w;
    }

}
