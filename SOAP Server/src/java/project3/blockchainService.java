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
    @WebMethod(operationName = "viewChain")
    public String viewChain() {
        return bc.toString();
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addBlock")
    public void addBlock(@WebParam(name = "difficulty") int difficulty, @WebParam(name = "transaction") String transaction) {
        //Create a new block with given information and add it to the blockchain
        Block newBlock = new Block(bc.getChainSize(), new Timestamp(System.currentTimeMillis()), transaction, difficulty);
        newBlock.proofOfWork();
        bc.addBlock(newBlock);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "validateChain")
    //Return true if the chain is valid, false otherwise
    public boolean validateChain() {
        if (bc.isChainValid()) {
            return true;
        } else {
            return false;
        }
    }

}
