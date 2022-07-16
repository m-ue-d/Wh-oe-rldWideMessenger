package spg.shared.network;

import io.netty.buffer.ByteBuf;
import spg.shared.utility.ByteBufImpl;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class PacketBuf extends ByteBufImpl {

    public PacketBuf(ByteBuf buf) {
        super(buf);
    }

    /**
     * Writes an variable-length integer to the buffer that may use less bytes than a regular integer.
     * @param value The integer to write.
     *
     * @see #readVarInt() to read the integer back.
     */
    public void writeVarInt(int value) {
        while ((value & -128) != 0) {
            writeByte(value & 127 | 128);
            value >>>= 7;
        }

        writeByte(value);
    }

    /**
     * Reads an variable-length integer from the buffer.
     * @return The integer that was read.
     */
    public int readVarInt() {
        int run = 0, len = 0;
        byte cur;

        do {
            cur = readByte();
            run |= (cur & 127) << len++ * 7;
            if (len > 5) {
                throw new RuntimeException("Var-Int too big");
            }
        } while ((cur & 128) == 128);

        return run;
    }

    /**
     * Writes a string of any length to the buffer.
     * @param str The string to write.
     *
     * @see #readString() to read the string back.
     */
    public void writeString(String str) {
        writeVarInt(str.length());
        writeBytes(str.getBytes());
    }

    /**
     * Reads a string of any length from the buffer.
     * @return The string read.
     *
     * @see #writeString(String) to write the string to the buffer.
     */
    public String readString() {
        int len = readVarInt();
        return readBytes(len).toString(StandardCharsets.UTF_8);
    }

    /**
     * Writes an RSA Key to the buffer.
     * @param key The key to write.
     *
     * @see #readRSAKey() to read the key back.
     */
    public void writeRSAKey(BigInteger key) {
        writeVarInt(key.bitLength());
        writeBytes(key.toByteArray());
    }

    /**
     * Reads an RSA Key from the buffer.
     * @return The key read.
     *
     * @see #writeRSAKey(BigInteger) to write the key to the buffer.
     */
    public BigInteger readRSAKey() {
        int len = readVarInt();
        return new BigInteger(
            readBytes(len).toString(StandardCharsets.UTF_8)
        );
    }
}
