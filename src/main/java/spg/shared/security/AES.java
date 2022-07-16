package spg.shared.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES {

    /**
     * Generates a new AES key.
     * @return The symmetric key.
     */
    public static String genKey(){
        StringBuilder key= new StringBuilder();
        SecureRandom r= new SecureRandom();
        for (int i=0;i<64;i++) {
            int x = r.nextInt(129);
            key.append((char) x);
        }
        return key.toString();
    }

    private static SecretKeySpec getKeySpec(final String myKey) {
        MessageDigest sha;
        byte[] key;

        try {
            key = myKey.getBytes(StandardCharsets.UTF_8);
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            return new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(final String strToEncrypt, final String secret) {
        try {
            SecretKeySpec secretKey = getKeySpec(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder()
                    .encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e);
        }
        return "";
    }

    public static String decrypt(final String strToDecrypt, final String secret) {
        try {
            SecretKeySpec secretKey = getKeySpec(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder()
                .decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e);
            return "";
        }
    }
}