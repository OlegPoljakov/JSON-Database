package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    @Parameter(names = "-t", description = "Type of request")
    private String type;

    @Parameter(names = "-i", description = "Index of item in db")
    private int index;

    @Parameter(names = "-m", description = "Value to be saved")
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

        switch (type) {
            case("set"):
                output.writeUTF("set");
                output.writeUTF(Integer.toString(index));
                output.writeUTF(value);
                System.out.println("Sent: " + type + " " + index + " " + value);
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            case("get"):
                output.writeUTF("get");
                output.writeUTF(Integer.toString(index));
                System.out.println("Sent: " + type + " " + index);
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            case("delete"):
                output.writeUTF("delete");
                output.writeUTF(Integer.toString(index));
                System.out.println("Sent: " + type + " " + index);
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            case ("exit"):
                System.out.println("Sent: " + type);
                output.writeUTF("exit");
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                break;

            default:
                break;
        }
    }
}
