package storage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LocalStorage {

    private static JSONParser parser = new JSONParser();
    private static final String path = new File("").getAbsolutePath()+ File.separator + "src" + File.separator + "storage" + File.separator + "storage.json";


    public static JSONObject readFile() {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try{
            obj = (JSONObject) parser.parse(new FileReader(path));
        }catch(Exception e){

        }
        return obj;
    }

    public static String readFromFileByKey(String key){
        JSONObject jsonObject = readFile();
        return (String) jsonObject.get(key);
    }

    public static void writeFile(String key, String value) {
        JSONObject jsonObject = readFile();
        jsonObject.put(key, value);
        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}