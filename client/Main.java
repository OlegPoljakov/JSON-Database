package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;
import server.cli.CommandLineArgs;
import server.cli.requests.Request;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int PORT = 8000;

    public static void main(String[] args) {

        System.out.println("Client Started");

        CommandLineArgs cla = new CommandLineArgs();
        JCommander jCommander = new JCommander(cla);
        jCommander.setProgramName("JSON Database");

        JCommander.newBuilder()
                .addObject(cla)
                .build()
                .parse(args);

        try (
                Socket socket = new Socket(InetAddress.getByName(ADDRESS), PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String request = cla.toJson();
            output.writeUTF(request);
            System.out.printf("Sent: %s \n", request);
            System.out.print("Received: " + input.readUTF());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
