package classes;

import storage.LocalStorage;

public class AC {

    private String nounce; // S(t,i)
    private Double res;
    //private Integer[] w;

    public AC() {
    }

    public void init() {
        this.nounce = LocalStorage.readFromFileByKey("nounce");
        this.res = Double.parseDouble(LocalStorage.readFromFileByKey("res"));
        //getWindowFromFile();
    }

    // // cast from STRING to INTEGER[]
    // private void getWindowFromFile() {
    //     String str = LocalStorage.readFromFileByKey("window");
    //     this.w = JSONConverter.toObject(str, Integer[].class);
    // }

    public String getNounce() {
        return this.nounce;
    }

    public Double getResult() {
        return this.res;
    }
    
    // public Integer[] getWindow(){
    //     return this.w;
    // }

}
