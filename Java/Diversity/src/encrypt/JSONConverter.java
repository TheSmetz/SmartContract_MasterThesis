package encrypt;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JSONConverter {

    public static <T> String toJSON(T entity) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String msgJson = gson.toJson(entity);
        return msgJson;
    }

    public static <T> T toObject(String json, Class<T> classType) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        T jsonObject = gson.fromJson(json, classType);
        return jsonObject;
    }
    
}
