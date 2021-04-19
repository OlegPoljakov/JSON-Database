package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static  final int PORT = 23456;

    public static void main(String[] args) throws IOException {

        ServerSocket listener = new ServerSocket(PORT);

        System.out.println("Server started!");
        Socket client = listener.accept();

        DataInputStream input = new DataInputStream(client.getInputStream());
        DataOutputStream output = new DataOutputStream(client.getOutputStream());

        String msg = input.readUTF(); // reading a message
        String numberinString = input.readUTF(); // reading a message

        System.out.println("Received: " + msg + numberinString);

        //msg = "A record # 12 was sent!";
        output.writeUTF("A record # " + numberinString + " was sent!"); // resend it to the client
        System.out.println("Sent: " + "A record # " + numberinString + " was sent!");
    }
}
