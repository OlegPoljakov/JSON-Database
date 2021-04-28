package server.cli.commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.exceptions.NoSuchKeyException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
        JSONArray listofvalues = new JSONArray();
        boolean keyisfound = false;

        try (FileReader reader = new FileReader("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json")) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            listofvalues = (JSONArray) obj;   //Array of json items
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


        for (int i=0; i < listofvalues.size(); i++){
            JSONObject itemArr = (JSONObject)listofvalues.get(i);
            if(itemArr.get("key").toString().equals(key)) {
                result = (String) itemArr.get("value");
                keyisfound = true;
            }
        }

        if (!keyisfound) {
            throw new NoSuchKeyException();
        }
    }
}