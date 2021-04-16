package server;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static String[] intArray = new String[100];

    public static void main(String[] args) {

        Arrays.fill(intArray, "");
        Scanner sc = new Scanner (System.in);
        String command = "";
        int numofcell = 0;
        String text = "";

        do {
            System.out.print("> ");
            command =  sc.next();
            //numofcell = sc.nextInt() - 1;
            //text = sc.nextLine();

            switch (command) {
                case("set"):
                    numofcell = sc.nextInt() - 1;
                    text = sc.nextLine();
                    if (numofcell>=0 && numofcell<=99) {
                        intArray[numofcell] = text;
                        System.out.println("OK");
                    }
                    break;
                case("get"):
                    numofcell = sc.nextInt() - 1;
                    if (numofcell>=0 && numofcell<=99 && !intArray[numofcell].isEmpty()) {
                        System.out.println(intArray[numofcell]);
                    }
                    else{
                        System.out.println("ERROR");
                    }
                    break;
                case("delete"):
                    numofcell = sc.nextInt() - 1;
                    if (numofcell>=0 && numofcell<=99) {
                        intArray[numofcell] = "";
                        System.out.println("OK");
                    }
                    else {
                        System.out.println("ERROR");
                    }
                    break;
                default:
                    break;
            }
        }
        while (!command.equals("exit"));
    }
}
