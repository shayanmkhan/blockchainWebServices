/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project3;

import java.sql.Timestamp;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author shayankhan
 */
@WebService(serviceName = "blockchainService")
public class blockchainService {

    static BlockChain bc = new BlockChain();

    /**
     * Web service operation
     */
    @WebMethod(operationName = "handleRequest")
    public String handleRequest(@WebParam(name = "request") String request) {
        //Parse request
        String[] requestAttributes = request.split(",");
        int operation = Integer.parseInt(requestAttributes[0]);

        switch (operation) {
            case 0:
                //Save given parameters
                int difficulty = Integer.parseInt(requestAttributes[1]);
                String transaction = requestAttributes[2];

                //Call addBlock method with given parameters
                addBlock(difficulty, transaction);
                return "Block added successfully.";
            case 1:
                //Call validateChain method and return result
                if (validateChain()) {
                    return "The blockchain is valid.";
                } else {
                    return "The blockchain is not valid.";
                }
            case 2:
                return viewChain();
        }

        return null;
    }

    //Returns JSON string representation of entire blockchain
    private String viewChain() {
        return bc.toString();
    }

    //Creates a new block with given information and add it to the blockchain
    private void addBlock(int difficulty, String transaction) {
        Block newBlock = new Block(bc.getChainSize(), new Timestamp(System.currentTimeMillis()), transaction, difficulty);
        newBlock.proofOfWork();
        bc.addBlock(newBlock);
    }

    //Returns true if the blockchain is valid, false otherwise
    private boolean validateChain() {
        if (bc.isChainValid()) {
            return true;
        } else {
            return false;
        }
    }
}
