
package project3task3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shayankhan
 */
//A RESTful servlet for blockchain operations
@WebServlet(name = "BlockchainService", urlPatterns = {"/BlockchainService/*"})
public class BlockchainService extends HttpServlet {

    // A global blockchain to store transactions
    private static BlockChain bc = new BlockChain();

    // GET is used for validation checks and viewing the blockchain
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Parse the input to extract the given operation
        String result = "";
        String operation = request.getPathInfo().substring(1);
        System.out.println(operation);

        //Perform the given operation
        if (operation.equals("View")) {
            result = bc.toString();
        } else {
            if (bc.isChainValid()) {
                result = "The blockchain is valid.";
            } else {
                result = "The blockchain is not valid.";
            }
        }

        // Things went well so set the HTTP response code to 200 OK
        response.setStatus(200);
        // tell the client the type of the response
        response.setContentType("text/plain;charset=UTF-8");

        // return the value from a GET request
        PrintWriter out = response.getWriter();
        out.println(result);
    }

    // POST is used for storing new transcations
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Read request from socket
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String data = br.readLine();
        String[] dataAttributes = data.split(",");

        //Extract attributes from request data 
        int difficulty = Integer.parseInt(dataAttributes[0]);
        String transaction = dataAttributes[1];
        
        //Create a new block with given information and add it to the blockchain
        Block newBlock = new Block(bc.getChainSize(), new Timestamp(System.currentTimeMillis()), transaction, difficulty);
        newBlock.proofOfWork();
        bc.addBlock(newBlock);

        response.setStatus(200);
        return;
    }

}
