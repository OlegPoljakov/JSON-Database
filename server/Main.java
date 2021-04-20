package server;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final int PORT = 23456;
    private static String[] intArray = new String[1000];
    private static Map<String, String> DataBase = new HashMap<>();

    public static void main(String[] args) throws IOException, JSONException {

        Arrays.fill(intArray, "");

        String type = "";
        String index = "";
        String text = "";
        String inputJson = "";
        String outputjson= "";

        Gson gson = new Gson();

        ServerSocket listener = new ServerSocket(PORT);
        System.out.println("Server started!");


        outerloop:
        while (!type.equals("exit")) { //This might be changed

            Socket client = listener.accept();

            DataInputStream input = new DataInputStream(client.getInputStream());
            DataOutputStream output = new DataOutputStream(client.getOutputStream());

            inputJson = input.readUTF(); //Read client answer in json format string
            JSONObject obj = new JSONObject(inputJson);
            type = obj.getString("type");

            switch (type) {
                case ("set"):
                    Map<String, String> setcommand = new HashMap<>();

                    index = obj.getString("key");
                    text = obj.getString("value");

                    DataBase.put(index,text);

                    setcommand.put("response", "OK");
                    outputjson = gson.toJson(setcommand);
                    output.writeUTF(outputjson);

                    break;

                case ("get"):
                    index = obj.getString("key");
                    Map<String, String> getcommand = new HashMap<>();
                    if (DataBase.containsKey(index)) {
                        getcommand.put("response", "OK");
                        getcommand.put("value", DataBase.get(index));
                        outputjson = gson.toJson(getcommand);
                        output.writeUTF(outputjson);
                    } else {
                        getcommand.put("response", "ERROR");
                        getcommand.put("reason", "No such key");
                        outputjson = gson.toJson(getcommand);
                        output.writeUTF(outputjson);
                    }
                    break;

                case ("delete"):
                    index = obj.getString("key");
                    Map<String, String> deletecommand = new HashMap<>();
                    if (DataBase.containsKey(index)) {
                        if (!DataBase.get(index).equals("")) {
                            DataBase.remove(index);
                            deletecommand.put("response", "OK");
                        }
                    } else {
                        deletecommand.put("response", "ERROR");
                        deletecommand.put("reason", "No such key");
                    }

                    outputjson = gson.toJson(deletecommand);
                    output.writeUTF(outputjson);
                    break;

                case("exit"):
                    Map<String, String> exitcommand = new HashMap<>();
                    exitcommand.put("response", "OK");
                    outputjson = gson.toJson(exitcommand);
                    output.writeUTF(outputjson);
                    break outerloop;
                default:
                    break;
            }

        }
        listener.close();
    }
}