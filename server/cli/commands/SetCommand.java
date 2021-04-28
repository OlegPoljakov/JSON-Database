package server.cli.commands;

import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.exceptions.NoSuchKeyException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SetCommand implements Command {

    private final String key;
    private final String value;
    private static FileWriter file;
    private JSONArray datalist;
    private boolean keyfound = false;

    public SetCommand(String key, String value, JSONArray datalist) {
        this.key = key;
        this.value = value;
        this.datalist = datalist;
    }

    @Override
    public void execute() {

        JSONArray listofvalues = new JSONArray();
        boolean keyisfound = false;
        Object obj = new JsonObject();

        try (FileReader reader = new FileReader("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json")) {
            JSONParser jsonParser = new JSONParser();
            obj = jsonParser.parse(reader);
            //listofvalues = (JSONArray) obj;   //Array of json items
        }
        catch (FileNotFoundException e) {
            throw new NoSuchKeyException();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new NoSuchKeyException();
        }


        try{
            listofvalues = (JSONArray) obj;   //Array of json items
            for (int i = 0; i < listofvalues.size(); i++) {
                JSONObject itemArr = (JSONObject) listofvalues.get(i);
                if (itemArr.get("key").toString().equals(key)) {
                    itemArr.put("value", value);
                    keyisfound = true;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (!keyisfound) {
            JSONObject information = new JSONObject();
            information.put("key", key);
            information.put("value", value);
            listofvalues.add(information);
        }

        try {
            file = new FileWriter("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json");
            listofvalues.writeJSONString(listofvalues, file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}