package server.cli.commands;

//import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.database.Database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetCommand implements Command {

    private final String key;
    private final String value;
    private static FileWriter file;
    private JSONArray datalist;

    public SetCommand(String key, String value, JSONArray datalist) {
        this.key = key;
        this.value = value;
        this.datalist = datalist;
    }

    @Override
    public void execute() {

        boolean iskeyinarray = false;

        JSONArray listofvalues = new JSONArray();

        //try (FileReader reader = new FileReader("./src/server/data/")){
        try (FileReader reader = new FileReader("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json")) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            if (obj.toString().length() == 0) {
                listofvalues = (JSONArray) obj;   //Array of json items

                //Тут мы заменяем (обновляем) значение по ключу, если таковой присутствует в массиве
                for (int i=0; i < listofvalues.size(); i++){
                    JSONObject itemArr = (JSONObject)listofvalues.get(i);
                    if(itemArr.get("key").toString().equals(key)) {
                        itemArr.put("value", value);
                        iskeyinarray = true;
                    }
                }
            }
            else {
                JSONObject information = new JSONObject();
                information.put("key", key);
                information.put("value", value);
                listofvalues.add(information);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Если такой ключ не присутствует, то добавляем элемент в массив json
        /*
        if (!iskeyinarray) {
            JSONObject information = new JSONObject();
            information.put("key", key);
            information.put("value", value);
            listofvalues.add(information);
        }
        */

        try{
            file = new FileWriter("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json");
            //file = new FileWriter("./src/server/data/");
            listofvalues.writeJSONString(listofvalues, file);
            //file.write(listofvalues.toJSONString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}