package server;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static final int PORT = 23456;
    private static String[] intArray = new String[1000];

    public static void main(String[] args) throws IOException {

        Arrays.fill(intArray, "");

        String type = "";
        int index = 0;
        String text = "";

        ServerSocket listener = new ServerSocket(PORT);
        System.out.println("Server started!");

        outerloop:
        while (!type.equals("exit")) {

            Socket client = listener.accept();

            DataInputStream input = new DataInputStream(client.getInputStream());
            DataOutputStream output = new DataOutputStream(client.getOutputStream());

            type = input.readUTF();
            switch (type) {
                case ("set"):
                    index = Integer.parseInt(input.readUTF()) - 1;
                    text = input.readUTF();
                    if (index >= 0 && index <= 999) {
                        intArray[index] = text;
                        output.writeUTF("OK");
                    }
                    break;

                case ("get"):
                    index = Integer.parseInt(input.readUTF()) - 1;
                    if (index >= 0 && index <= 99 && !intArray[index].isEmpty()) {
                        output.writeUTF(intArray[index]);
                    } else {
                        output.writeUTF("ERROR");
                    }
                    break;

                case ("delete"):
                    index = Integer.parseInt(input.readUTF()) - 1;
                    if (index >= 0 && index <= 99) {
                        intArray[index] = "";
                        output.writeUTF("OK");
                    } else {
                        output.writeUTF("ERROR");
                    }
                    break;

                case("exit"):
                    output.writeUTF("OK");
                    break outerloop;
                default:
                    break;
            }
        }
        listener.close();
    }
}