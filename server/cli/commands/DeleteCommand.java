package server.cli.commands;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.exceptions.NoSuchKeyException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DeleteCommand implements Command{

    private final String key;
    private static FileWriter file;

    public DeleteCommand(String key) {
        this.key = key;
    }
    @Override
    public void execute() {

        JSONArray listofvalues = new JSONArray();
        Integer position = null;

        //try (FileReader reader = new FileReader("./src/server/data/")){
        try (FileReader reader = new FileReader("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json")) {
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(reader);
            listofvalues = (JSONArray) obj;   //Array of json items
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i=0; i < listofvalues.size(); i++){
            JSONObject itemArr = (JSONObject)listofvalues.get(i);
            if(itemArr.get("key").toString().equals(key)) {
                position = i;
            }
        }

        JSONArray list = new JSONArray();
        int len = listofvalues.size();
        if (position != null) {
            for (int i=0;i<len;i++) {
                if (i != position)
                {
                    list.add(listofvalues.get(i));
                }
            }
        }
        else
        {
            throw new NoSuchKeyException();
        }


        try{
            file = new FileWriter("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json");
            file.write(list.toJSONString());
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
