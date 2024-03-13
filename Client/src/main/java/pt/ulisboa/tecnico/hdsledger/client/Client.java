package pt.ulisboa.tecnico.hdsledger.client;

import java.util.Scanner;

//  client CLI
public class Client {

    public static void main(String[] args){

        final Scanner scanner = new Scanner(System.in);
        final String clientId = args[0];
        
        while(true){
            System.out.println("========== HDS Client ========== ");
            System.out.println("\n       !! Welcome to HDS² !!\n");
            System.out.println("\nThe Client ID assined to you is: " + clientId + "\n");
            String line = scanner.nextLine();

            // Empty command
            if (line.trim().length() == 0) {
                System.out.println();
                continue;
            }

            switch (line) {
                case "exit" -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                }
                default -> {
                    System.out.println(line);
                    break;
                }    
            }
        }
    }
}