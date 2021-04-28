package server.cli.requests;

import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Request {

    /*
    private static final Path DATA_DIR_PATH = Paths.get(
            "src" + File.separator +
                    "client" + File.separator +
                    "data").toAbsolutePath();
    */

    @Parameter(names = {"-t", "--type"}, description = "The type of the request")
    private String type;

    @Parameter(names = {"-k", "--key"}, description = "The Record key")
    private String key;

    @Parameter(names = {"-v", "--value"}, description = "The text value to add")
    private String value;

    //Название файла для чтения
    @Parameter(names = {"-in", "--commandfromfile"}, description = "The text with commands from file")
    private String commandfromfile;

    public Request() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCommandfromfile() {
        return commandfromfile;
    }

    public void setCommandfromfile(String commandfromfile) {
        this.commandfromfile = commandfromfile;
    }


    //Чтение из файла - результат - строка
    private String readFromFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    //Получаем JSON из запроса
    public String toJson() {
        if (commandfromfile != null) {
            try {
                return readFromFile("D:\\Java\\HyperSkill Projects\\JSON Database\\JSON Database\\task\\src\\client\\data\\" + commandfromfile); //смотреть выше - возвращает строку type, key, value.
            } catch (IOException e) {
                System.out.println("Cannot read file: " + e.getMessage());
                System.exit(1);
            }
        }

        Map<String, String> map = new LinkedHashMap<>();
        map.put("type", type);
        map.put("key",key);
        map.put("value",value);
        return new Gson().toJson(map); //{type type, key key, value value}
    }
}
