package classes;

import java.util.function.Function;

import encrypt.JSONConverter;

public class Contract {
    
    public Function<Integer[],Double> getFunction(){
        Function<Integer[],Double> f = new Function<Integer[],Double>(){
            @Override
            public Double apply(Integer[] t) {
                int sum = 0;
                for (Integer integer : t) {
                    sum += integer;
                }
                return (double) (sum / t.length);
            }
            
        };
        return f;
    }

    public String getContract(){
        return JSONConverter.toJSON(this);
    }
}
