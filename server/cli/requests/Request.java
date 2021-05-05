package server.cli.requests;

import com.beust.jcommander.Parameter;
import com.google.gson.JsonElement;


public class Request {

    @Parameter(names = {"-t", "--type"}, description = "The type of the request")
    private String type;

    @Parameter(names = {"-k", "--key"}, description = "The Record key")
    private JsonElement key;

    @Parameter(names = {"-v", "--value"}, description = "The text value to add")
    private JsonElement value;

    //Название файла для чтения
    @Parameter(names = {"-in", "--commandfromfile"}, description = "The text with commands from file")
    private String commandfromfile;


    public Request() {
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKey(JsonElement key) {
        this.key = key;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }
}

