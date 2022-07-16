package spg.shared.security;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * p = Primzahl 1
 * q = Primzahl 2
 * N = Teil beider Keys
 *
 * phi = Eulersche phi-funktion
 * e = Teil des public key
 * d = Teil des private key
 */
public class RSA {

    private static final int bitLength = 4096;

    /**
     * Generate a new RSA keypair. The keypair consists of a private and public key + a shared modulus N.
     * @return A new RSA keypair.
     */
    public static Keychain genKeyPair_plusN() {
        // Note: Der braucht manchmal über 30 sekunden zu generieren xD
        //  Kann man den irgendwie schneller machen?
        SecureRandom r= new SecureRandom();
        BigInteger p= BigInteger.probablePrime(bitLength,r);
        BigInteger q= BigInteger.probablePrime(bitLength,r);
        // Bis dahin ----------------------------------------
        BigInteger N= p.multiply(q);
        //Da p und q primzahlen sind, muss man nur 1 wegrechnen (Mathe henker)
        BigInteger phi= p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e= BigInteger.probablePrime(bitLength /2,r);

        while(phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0){
            e= e.add(BigInteger.ONE);
        }
        BigInteger d= e.modInverse(phi);

        // nun die generierten Schlüssel übergeben
        return new Keychain(
            new BigInteger[]{
                d, //private key
                e, //public key
                N  //n
            }
        );
    }

    /**
     * Encrypts the given plaintext with the public key.
     * @param text The plaintext to encrypt.
     * @param key The public key.
     * @param N The modulus.
     * @return The ciphertext.
     */
    public static byte[] encrypt(byte[] text, BigInteger key, BigInteger N) {
        return new BigInteger(text)
            .modPow(key,N)
            .toByteArray();
    }

    /**
     * Decrypts the given ciphertext with the private key.
     * @param cipher The ciphertext to decrypt.
     * @param key The private key.
     * @param N The modulus.
     * @return The plaintext.
     */
    public static byte[] decrypt(byte[] cipher, BigInteger key, BigInteger N) {
        return new BigInteger(cipher)
            .modPow(key,N)
            .toByteArray();
    }
}
