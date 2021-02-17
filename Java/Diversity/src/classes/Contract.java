package classes;

import java.util.function.Function;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Contract {
    private Function<Integer[],Double> f = new Function<Integer[],Double>(){
        @Override
        public Double apply(Integer[] t) {
            int sum = 0;
            for (Integer integer : t) {
                sum += integer;
            }
            return (double) (sum / t.length);
        }
        
    };
    
    public Function<Integer[],Double> getFunction(){
        return this.f;
    }

    public String getContract(){
        return StringToJson(this);
    }

    public static String StringToJson(Contract c) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String msgJson = gson.toJson(c);
        return msgJson;
    }
}
