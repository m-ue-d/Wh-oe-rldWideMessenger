package spg.client;

import java.math.BigInteger;
import java.util.HashMap;

public class Packet {



    public static void main(String[] args) {
        Packet p= new Packet(null, "hello guys!", null);
        p.encrypt(null,null);
    }

    private final String id;
    private String message;
    private HashMap<String, BigInteger> userKeys = new HashMap<String,BigInteger>();


    public Packet(ClientMain[] receivers, String message, BigInteger threadKey){
        /*
        Zuerst die message verschlüsseln und dann den Schlüssel mit der methode unten mit den einzelnen Keys verschlüsseln, die man dann in die Hashmap gibt
        */


        this.id= AES.genKey();
        this.message=message;   //nötige checks noch machen
    }

    public void encrypt(String threadKey, ClientMain[] receivers) {
        //erst message, dann den threadkey in die Hashmap
        AES aes = new AES();
        String key = AES.genKey();
        System.out.println(key);
        System.out.println(message);
        this.message = aes.encrypt(message, key);
        System.out.println(message);
        System.out.println(aes.decrypt(message, key));


        for (ClientMain client:receivers) { //hashmap befüllen
            byte[] encodedThreadKey=  RSA.encode(key,client.publicKey, client.N);
        }

        //hex hex (insert zauber noises)
    }

}
