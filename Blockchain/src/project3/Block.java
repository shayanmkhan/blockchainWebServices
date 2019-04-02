/**
 *
 * @author shayankhan
 * @description This class represents single block in a blockchain.
 */

package project3;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class Block {
    
    private int index;
    private Timestamp timestamp;
    private String data;
    private String previousHash;
    private BigInteger nonce;
    private int difficulty;
    
    Block(int index, Timestamp timestamp, String data, int difficulty) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.difficulty = difficulty;
        this.nonce = BigInteger.ZERO;
    }
    
    //Calculates a SHA-256 hash based on the member variables of the block
    public String calculateHash() {
        return hash(index + timestamp.toString() + data + previousHash + nonce + difficulty);
    }
    
    public BigInteger getNonce() {
        return nonce;
    }
    
    //Calculates the "proof of work" of the block, i.e. a hash that has the number of leading
    //zeroes specified by the block's difficulty parameter
    public String proofOfWork() {
        //Setup
        String hash = calculateHash();
        boolean foundHash = false;
        
        //Increment nonce and recompute hash until it has the required number of leading zeroes
        while(foundHash == false) {
            foundHash = true;

            //Check each character until the difficulty level is met
            for(int i = 0; i < difficulty; i++) {
                if(!(hash.charAt(i) == '0')) {
                    foundHash = false;
                    nonce = nonce.add(BigInteger.ONE);
                    hash = calculateHash();
                    break;
                }
            }
            
        }
        
        return hash;
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
    
    //Returns a JSON string representation of the block
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }
    
    public String getPreviousHash() {
        return previousHash;
    }
    
    public int getIndex() {
        return index;
    }
    
    public void setIndex(int index) {
        this.index = index;
    }
    
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
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
        }
        catch (NoSuchAlgorithmException nsa) {
            System.out.println("No such algorithm exception thrown " + nsa);
        }
        catch (UnsupportedEncodingException uee ) {
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
                if ((0 <= halfbyte) && (halfbyte <= 9)) 
                    buf.append((char) ('0' + halfbyte));
                else 
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        } 
        return buf.toString();
    }
    
    //Simple driver for testing
    public static void main(String[] args) {
        Block b = new Block(0, new Timestamp(System.currentTimeMillis()), "Hello", 1);
        b.setPreviousHash("Random");
        b.proofOfWork();
    }
}
