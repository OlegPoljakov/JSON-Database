package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 23456;

    public static void main(String[] args) throws IOException {

        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);

        System.out.println("Client started!");

        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output  = new DataOutputStream(socket.getOutputStream());

        int number = 12;
        String numberinString = Integer.toString(number);
        String msg = "Give me a record # ";
        output.writeUTF(msg);
        output.writeUTF(numberinString);

        System.out.println("Sent: " + msg + numberinString);

        String receivedMsg = input.readUTF(); // response message
        System.out.println("Received: " + receivedMsg);
    }
}
