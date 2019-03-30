/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.util.Scanner;

/**
 *
 * @author shayankhan
 */
public class Project3Task2Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Set up user input
        Scanner input = new Scanner(System.in);
        int choice = -1;

        //Display menu until user decides to exit
        while (choice != 3) {
            System.out.println("Blockchain Menu");
            System.out.println("0. Add a transaction to the blockchain");
            System.out.println("1. Verify the blockchain");
            System.out.println("2. View the blockchain");
            System.out.println("3. Exit");
            choice = Integer.parseInt(input.nextLine());
            System.out.println();

            switch (choice) {
                case 0:
                    //Accept input from user
                    System.out.println("Enter the difficulty level for the block:");
                    String difficulty = input.nextLine();
                    System.out.println("Enter the transaction to be added:");
                    String transaction = input.nextLine();
                    
                    //Create request string to send to server
                    String request = "0," + difficulty + "," + transaction;

                    //Send request to server, and calculate the amount of time it takes
                    long startTime = System.currentTimeMillis();
                    handleRequest(request);
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    //Output results
                    System.out.println("\nBlock successfully added in " + elapsedTime + "ms.");
                    break;
                case 1:
                    //Create request string to send to server
                    request = "1";
                    
                    //Send request to server, and calculate the amount of time it takes
                    startTime = System.currentTimeMillis();
                    String response = handleRequest(request);
                    elapsedTime = System.currentTimeMillis() - startTime;

                    //Output results
                    System.out.println(response);
                    System.out.println("Blockchain validation took " + elapsedTime + "ms.");
                    break;
                case 2:
                    //Create request string to send to server
                    request = "2";

                    //Send request to server, and output response
                    response = handleRequest(request);
                    System.out.println(response);
                    break;
            }

            System.out.println();
        }
    }

    private static String handleRequest(java.lang.String request) {
        project3.BlockchainService_Service service = new project3.BlockchainService_Service();
        project3.BlockchainService port = service.getBlockchainServicePort();
        return port.handleRequest(request);
    }

}
