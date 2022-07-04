package spg.shared.security;

import java.math.BigInteger;

public class SecurityKeychain {

    private final BigInteger[] keys;

    public SecurityKeychain(BigInteger[] keys) {
        this.keys = keys;
    }

    public BigInteger getPrivateKey() {
        return keys[0];
    }

    public BigInteger getPublicKey() {
        return keys[1];
    }

    public BigInteger getSecretKey() {
        return keys[2];
    }
}
