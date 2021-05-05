package server.cli.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import server.database.Database;
import server.exceptions.NoSuchKeyException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SetCommand implements Command {

    private final JsonElement key;
    private final JsonElement value;

    public SetCommand(JsonElement key, JsonElement value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute() {
        Database.INSTANCE.set(key, value);
    }
}