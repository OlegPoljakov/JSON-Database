package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    @Parameter(names = "-t", description = "Type of request")
    private String type;

    @Parameter(names = "-k", description = "Key")
    private String key;

    @Parameter(names = "-v", description = "Value to be saved")
    private String value;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Client started!");

        Main main = new Main();

        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output  = new DataOutputStream(socket.getOutputStream());

        main.run(socket, input, output);
        socket.close();
    }

    public void run(Socket socket, DataInputStream input, DataOutputStream output) throws IOException {

        String receivedMsg = "";

        Map<String, String> setcommand = new HashMap<>();
        String outputjson= "";
        Gson gson = new Gson();

        switch (type) {
            case("set"):
                setcommand.put("type", "set");
                //setcommand.put("key", Integer.toString(index));
                setcommand.put("key", key);
                setcommand.put("value", value);

                outputjson = gson.toJson(setcommand);
                System.out.println("Sent: " + outputjson);
                output.writeUTF(outputjson);

                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            case("get"):
                setcommand.put("type", "get");
                //setcommand.put("key", Integer.toString(index));
                setcommand.put("key", key);

                outputjson = gson.toJson(setcommand);
                System.out.println("Sent: " + outputjson);
                output.writeUTF(outputjson);

                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            case("delete"):
                setcommand.put("type", "delete");
                //setcommand.put("key", Integer.toString(index));
                setcommand.put("key", key);

                outputjson = gson.toJson(setcommand);
                System.out.println("Sent: " + outputjson);
                output.writeUTF(outputjson);

                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            case ("exit"):
                setcommand.put("type", "exit");
                outputjson = gson.toJson(setcommand);
                System.out.println("Sent: " + outputjson);
                output.writeUTF(outputjson);

                output.writeUTF("exit");
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            default:
                break;
        }
    }
}
