package project3;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author shayankhan
 * @description This class represents a blockchain comprised of individual
 * blocks
 */
public class BlockChain {

    List<Block> blocks;
    String chainHash;

    BlockChain() {
        blocks = new ArrayList<>();
        chainHash = "";
        
        //Create genesis block and add it to the blockchain
        Block genesis = new Block(0, new Timestamp(System.currentTimeMillis()), "genesis", 2);
        addBlock(genesis);
    }

    public Timestamp getTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    //Returns a reference to the most recently added block
    public Block getLatestBlock() {
        return blocks.get(blocks.size() - 1);
    }

    public int getChainSize() {
        return blocks.size();
    }

    //Calculates the number of hashes that the current system can compute per second
    public int hashesPerSecond() {
        //Setup for test
        String testString = "00000000";
        int numHashes = 0;
        long startTime = System.currentTimeMillis();

        //Repeatedly hash string
        while (true) {
            hash(testString);

            //Count number of hashes performed
            numHashes++;

            //Stop after a second has elapsed
            if (System.currentTimeMillis() - startTime >= 1000) {
                break;
            }
        }

        return numHashes;
    }

    //Add a block to the chain
    public void addBlock(Block newBlock) {
        //Set block's previous hash to hash of latest block
        if (getChainSize() == 0) {
            newBlock.setPreviousHash("");
        } else {
            Block previousBlock = getLatestBlock();
            newBlock.setPreviousHash(previousBlock.proofOfWork());
        }

        //Add block to blockchain and update chain hash
        blocks.add(newBlock);
        chainHash = newBlock.proofOfWork();
    }

    @Override
    //Returns a JSON string representation of the entire blockchain
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);

    }

    //Returns true if the blockchain is valid, and false otherwise
    public boolean isChainValid() {
        if (blocks.size() == 1) {
            //Setup
            Block block = getLatestBlock();
            String blockHash = block.calculateHash();
            int difficulty = block.getDifficulty();

            //Verify that hash has required number of leading zeroes
            for (int i = 0; i < difficulty; i++) {
                if (!(blockHash.charAt(i) == '0')) {
                    System.out.println("Improper hash on node " + block.getIndex()
                            + ": Does not begin with " + difficulty + " zeroes.");
                    return false;
                }
            }

            //Verify that chain hash is equal to block hash
            if (!chainHash.equals(blockHash)) {
                System.out.println("Chain hash does not match the hash of the most recently added block.");
                return false;
            }
        } else {
            for (int i = 1; i < blocks.size(); i++) {
                //Setup
                Block currentBlock = blocks.get(i);
                Block previousBlock = blocks.get(i - 1);
                String currentHash = currentBlock.calculateHash();
                String previousHash = previousBlock.calculateHash();
                int difficulty = currentBlock.getDifficulty();

                //Verify that hash of previous block matches previous hash pointer
                if (!currentBlock.getPreviousHash().equals(previousHash)) {
                    System.out.println("Improper hash on node " + currentBlock.getIndex()
                            + ": Previous hash does not match the hash of previous block.");
                    return false;
                }

                //Verify that hash has required number of leading zeroes
                for (int j = 0; j < difficulty; j++) {
                    if (!(currentHash.charAt(j) == '0')) {
                        System.out.println("Improper hash on node " + currentBlock.getIndex()
                                + ": Does not begin with " + difficulty + " zeroes.");
                        return false;
                    }
                }

            }

            //Verify that the chain hash is correct
            if (!chainHash.equals(getLatestBlock().calculateHash())) {
                System.out.println("Chain hash does not match the hash of the most recently added block.");
                return false;
            }
        }

        //If all verifications hold, return true
        return true;
    }

    //Traverses the chain and fixes any invalid features found
    public void repairChain() {
        //Always holds the hash of the previous block
        String previousHash = "";

        for (int i = 0; i < blocks.size(); i++) {
            //Setup
            Block currentBlock = blocks.get(i);
            String currentHash = currentBlock.calculateHash();
            int difficulty = currentBlock.getDifficulty();
            boolean foundError = false;

            //If previous hash pointer doesn't equal the hash of the previous block, 
            //set it to the hash of the previous block
            if (!currentBlock.getPreviousHash().equals(previousHash)) {
                foundError = true;
                currentBlock.setPreviousHash(previousHash);
            }

            //If hash doesn't have required number of leading zeroes, recompute proof of work
            for (int j = 0; j < difficulty; j++) {
                if (!(currentHash.charAt(j) == '0')) {
                    foundError = true;
                    break;
                }
            }

            //If an error was found, re-do proof of work and set it as the previous hash
            //Otherwise, set the current block's hash as the previous hash
            if (foundError) {
                previousHash = currentBlock.proofOfWork();
            } else {
                previousHash = currentBlock.calculateHash();
            }
        }

        //Verify that the chain hash is correct
        if (!chainHash.equals(getLatestBlock().calculateHash())) {
            chainHash = getLatestBlock().calculateHash();
        }

    }

    //Returns the hex representation of a string's SHA-256 hash
    public static String hash(String text) {

        try {
            // Create a SHA256 digest
            MessageDigest digest;
            digest = MessageDigest.getInstance("SHA-256");
            // allocate room for the result of the hash
            byte[] hashBytes;
            // perform the hash
            digest.update(text.getBytes("UTF-8"), 0, text.length());
            // collect result
            hashBytes = digest.digest();
            return convertToHex(hashBytes);
        } catch (NoSuchAlgorithmException nsa) {
            System.out.println("No such algorithm exception thrown " + nsa);
        } catch (UnsupportedEncodingException uee) {
            System.out.println("Unsupported encoding exception thrown " + uee);
        }
        return null;
    }

    // code from Stack overflow
    // converts a byte array to a string.
    // each nibble (4 bits) of the byte array is represented 
    // by a hex characer (0,1,2,3,...,9,a,b,c,d,e,f)
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    //Test driver
    public static void main(String[] args) {
        //Initialize blockchain
        BlockChain bc = new BlockChain();

        //Set up user input
        Scanner input = new Scanner(System.in);
        int choice = -1;

        //Display menu until user decides to exit
        while (choice != 6) {
            System.out.println("Blockchain Menu");
            System.out.println("0. View basic blockchain status");
            System.out.println("1. Add a transaction to the blockchain");
            System.out.println("2. Verify the blockchain");
            System.out.println("3. View the blockchain");
            System.out.println("4. Corrupt the chain");
            System.out.println("5. Hide the corruption by repairing the chain");
            System.out.println("6. Exit");
            choice = Integer.parseInt(input.nextLine());
            System.out.println();

            switch (choice) {
                case 0:
                    printStatus(bc);
                    break;
                case 1:
                    addBlock(bc);
                    break;
                case 2:
                    validateChain(bc);
                    break;
                case 3:
                    System.out.println(bc);
                    break;
                case 4:
                    corruptChain(bc);
                    break;
                case 5:
                    repairChain(bc);
                    break;

            }

            System.out.println();
        }
    }

    //Displays general features of the blockchain
    static void printStatus(BlockChain bc) {
        System.out.println("Number of blocks on chain: " + bc.blocks.size());
        System.out.println("Current hashes per second: " + bc.hashesPerSecond());
        System.out.println("Difficulty of the most recent block: " + bc.getLatestBlock().getDifficulty());
        System.out.println("Nonce for most recent blockchain hash: " + bc.getLatestBlock().getNonce());
        System.out.println("Chain hash: " + bc.chainHash);
    }

    //Creates a new block and adds it to the chain
    static void addBlock(BlockChain bc) {
        //Accept user input
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the difficulty level for the block:");
        int difficulty = Integer.parseInt(input.nextLine());
        System.out.println("Enter the transaction to be added:");
        String transaction = input.nextLine();

        //Create a new block with given information, add it to the blockchain, and calculate the amount of time
        //it takes to do so
        long startTime = System.currentTimeMillis();
        Block newBlock = new Block(bc.getChainSize(), new Timestamp(System.currentTimeMillis()), transaction, difficulty);
        newBlock.proofOfWork();
        bc.addBlock(newBlock);
        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("\nBlock successfully added in " + elapsedTime + "ms.");
    }

    //Displays whether the chain is valid
    static void validateChain(BlockChain bc) {
        //Determine whether chain is valid, and calculate how long the check takes
        long startTime = System.currentTimeMillis();
        boolean isChainValid = bc.isChainValid();
        long elapsedTime = System.currentTimeMillis() - startTime;

        //Output results
        if (isChainValid) {
            System.out.println("The blockchain is valid.");
        } else {
            System.out.println("The blockchain is not valid.");
        }
        System.out.println("Blockchain validation took " + elapsedTime + "ms.");
    }

    //Changes the data of a given block
    static void corruptChain(BlockChain bc) {
        //Accept user input
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the index of the block to corrupt:");
        int index = Integer.parseInt(input.nextLine());
        System.out.println("Enter the new transaction to be placed in the block:");
        String newTransaction = input.nextLine();

        //Change block based on user input
        Block corruptBlock = bc.blocks.get(index);
        corruptBlock.setData(newTransaction);
        System.out.println("\nBlock successfully modified.");
    }

    //Repairs all invalid features of the chain
    static void repairChain(BlockChain bc) {
        System.out.println("\nRepairing the chain...");
        
        //Call object's repairChain method and calculate how long it takes
        long startTime = System.currentTimeMillis();
        bc.repairChain();
        long elapsedTime = System.currentTimeMillis() - startTime;

        System.out.println("Blockchain successfully repaired in " + elapsedTime + "ms.");
    }

}
