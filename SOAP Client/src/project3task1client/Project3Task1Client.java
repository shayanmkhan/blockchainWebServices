/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3task1client;

import java.util.Scanner;

/**
 *
 * @author shayankhan
 */
public class Project3Task1Client {

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
                    int difficulty = Integer.parseInt(input.nextLine());
                    System.out.println("Enter the transaction to be added:");
                    String transaction = input.nextLine();

                    //Add block to chain with given parameters, and calculate the amount of time it takes
                    long startTime = System.currentTimeMillis();
                    addBlock(difficulty, transaction);
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    System.out.println("\nBlock successfully added in " + elapsedTime + "ms.");
                    break;
                case 1:
                    //Determine whether chain is valid, and calculate how long the check takes
                    startTime = System.currentTimeMillis();
                    boolean isChainValid = validateChain();
                    elapsedTime = System.currentTimeMillis() - startTime;

                    //Output results
                    if (isChainValid) {
                        System.out.println("The blockchain is valid.");
                    } else {
                        System.out.println("The blockchain is not valid.");
                    }
                    System.out.println("Blockchain validation took " + elapsedTime + "ms.");
                    
                    break;
                case 2:
                    //Display contents of the chain
                    System.out.println(viewChain());
                    break;
            }

            System.out.println();
        }
    }

    private static String viewChain() {
        project3.BlockchainService_Service service = new project3.BlockchainService_Service();
        project3.BlockchainService port = service.getBlockchainServicePort();
        return port.viewChain();
    }


    private static boolean validateChain() {
        project3.BlockchainService_Service service = new project3.BlockchainService_Service();
        project3.BlockchainService port = service.getBlockchainServicePort();
        return port.validateChain();
    }

    private static void addBlock(int difficulty, java.lang.String transaction) {
        project3.BlockchainService_Service service = new project3.BlockchainService_Service();
        project3.BlockchainService port = service.getBlockchainServicePort();
        port.addBlock(difficulty, transaction);
    }

}
