package spg.shared.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

class RSA
{
/*
    p   //primzahl 1
    q   //primzahl 2
    N   //Teil beider Keys
    phi //eulersche phi-funktion
    e   //Teil des public key
    d   //Teil des private key
*/

    private static final int bitlength = 4096;

    public static BigInteger[] genKeyPair_plusN(){  //first ist private, then is public, then is n
        SecureRandom r= new SecureRandom();
        BigInteger p= BigInteger.probablePrime(bitlength,r);
        BigInteger q= BigInteger.probablePrime(bitlength,r);
        BigInteger N= p.multiply(q);
        BigInteger phi= p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));   //Da p und q primzahlen sind, muss man nur 1 wegrechnen (Mathehenker)
        BigInteger e= BigInteger.probablePrime(bitlength/2,r);

        while(phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
            e= e.add(BigInteger.ONE);
        }
        BigInteger d= e.modInverse(phi);


        //nun die generierten Schlüssel übergeben
        BigInteger[] x= new BigInteger[3];
        x[0]=d;
        x[1]=e;
        x[2]=N;

        return x;
    }

    public static byte[] encrypt(String msg, BigInteger key, BigInteger N){  //key==e
        return (new BigInteger(msg.getBytes())).modPow(key,N).toByteArray();
    }
    //die Decode-Methode ist im Client, da man da den private-key braucht, den eben nur der Client hat =)


    /*public static Packet bytesToObject(byte[] encoded){
        try(ByteArrayInputStream is= new ByteArrayInputStream(encoded); ObjectInputStream oi= new ObjectInputStream(is)){
            return (Packet)oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }*/

}
