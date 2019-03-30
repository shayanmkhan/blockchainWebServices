
package project3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author shayankhan
 */
// A simple class to wrap a result.
class Result {

    String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

//A RESTful client for blockchain operations
public class Project3Task3Client {

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

                    //Add block to chain with given parameters, and calculate the amount of time it takes
                    long startTime = System.currentTimeMillis();
                    addBlock(difficulty, transaction);
                    long elapsedTime = System.currentTimeMillis() - startTime;

                    //Output results
                    System.out.println("\nBlock successfully added in " + elapsedTime + "ms.");
                    break;
                case 1:
                    //Determine whether chain is valid, and calculate how long the check takes
                    startTime = System.currentTimeMillis();
                    String isChainValid = validateChain();
                    elapsedTime = System.currentTimeMillis() - startTime;

                    //Output results
                    System.out.println(isChainValid);
                    System.out.println("Blockchain validation took " + elapsedTime + "ms.");

                    break;
                case 2:
                    //Display contents of the chain
                    System.out.print(viewChain());
                    break;
            }

            System.out.println();
        }
    }

    // Calls doPost method using given parameters. Return true if successful, false otherwise
    public static boolean addBlock(String difficulty, String transaction) {
        if (doPost(difficulty, transaction) == 200) {
            return true;
        } else {
            return false;
        }
    }

    //Calls doGet method, with the "View" operator as the parameter. Returns the result
    public static String viewChain() {
        Result r = new Result();
        int status = 0;
        if ((status = doGet("View", r)) != 200) {
            return "Error from server " + status;
        }
        return r.getValue();
    }

    //Calls doGet method, with the "Validate" operator as the parameter. Returns the result
    public static String validateChain() {
        Result r = new Result();
        int status = 0;
        if ((status = doGet("Validate", r)) != 200) {
            return "Error from server " + status;
        }
        return r.getValue();
    }

    // Low level routine to make an HTTP POST request
    // Note, POST does not use the URL line for its message to the server
    public static int doPost(String difficulty, String transaction) {

        int status = 0;
        String output;

        try {
            // Make call to a particular URL
            URL url = new URL("http://localhost:8080/Project3Task3Server/BlockchainService/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // set request method to POST and send name value pair
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            // write to POST data area
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
            out.write(difficulty + "," + transaction);
            out.close();

            // get HTTP response code sent by server
            status = conn.getResponseCode();

            //close the connection
            conn.disconnect();
        } // handle exceptions
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return HTTP status
        return status;
    }

    // Low level routine to make an HTTP GET request
    public static int doGet(String operation, Result r) {

        // Make an HTTP GET passing the name on the URL line
        r.setValue("");
        String response = "";
        HttpURLConnection conn;
        int status = 0;

        try {

            // pass the name on the URL line
            URL url = new URL("http://localhost:8080/Project3Task3Server/BlockchainService" + "//" + operation);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // tell the server what format we want back
            conn.setRequestProperty("Accept", "text/plain");

            // wait for response
            status = conn.getResponseCode();

            // If things went poorly, don't try to read any response, just return.
            if (status != 200) {
                // not using msg
                String msg = conn.getResponseMessage();
                return conn.getResponseCode();
            }
            String output = "";
            // things went well so let's read the response
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                response += output + "\n";

            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // return value from server 
        // set the response object
        r.setValue(response);
        // return HTTP status to caller
        return status;
    }

}
