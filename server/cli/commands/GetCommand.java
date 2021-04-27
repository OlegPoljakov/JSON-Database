package server.cli.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.database.Database;
import server.exceptions.FileIsEmptyException;
import server.exceptions.FileIsNotFoundException;
import server.exceptions.NoSuchKeyException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GetCommand implements Command{

    private final String key;
    private String result;

    public GetCommand(String key) {
        this.key = key;
    }

    public final String getResult() {
        return result;
    }

    @Override
    public void execute() {
        //result = Database.INSTANCE.get(key);
        /*
        if (database.containsKey(key)) {
            return database.get(key);
        }
        throw new NoSuchKeyException();
        */

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        //try (FileReader reader = new FileReader("./src/server/data/"))
        try (FileReader reader = new FileReader("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json"))
        {
            Object obj = jsonParser.parse(reader);   //Read JSON file
            try{
                JSONArray listofvalues = (JSONArray) obj;

                for (int i = 0; i < listofvalues.size(); i++) {
                    JSONObject tempobj = (JSONObject) listofvalues.get(i);
                    if (tempobj.get("key").equals(key)){
                        //result = tempobj.toJSONString();
                        result = (String) tempobj.get("value");
                    }
                }
            }
            catch (Exception e) {
                throw new NoSuchKeyException();
            }
            if (result == null) {
                throw new NoSuchKeyException();
            }
        }

        catch (FileNotFoundException e) {
            throw new NoSuchKeyException();
        }
         catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
