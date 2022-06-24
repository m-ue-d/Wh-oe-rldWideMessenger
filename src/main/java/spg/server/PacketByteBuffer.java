package spg.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

public class PacketByteBuffer implements Serializable {
    public static class Builder {
        private final StringBuilder content = new StringBuilder();

        public Builder writeMessage(String message) {
            content.append(message);
            return this;
        }
        public Builder writeTime(LocalDate time) {
            content.append(time);
            return this;
        }

        public Builder writeFile(File file) {
            try (FileInputStream fis = new FileInputStream(file)) {
                content.append(Arrays.toString(
                    fis.readAllBytes())
                );
            } catch (Exception e) {
                System.err.println("Error reading file");
            }
            return this;
        }

        public PacketByteBuffer build() {
            return new PacketByteBuffer(
                content.toString().getBytes()
            );
        }
    }

    private final byte[] content;

    public PacketByteBuffer(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public static PacketByteBuffer empty() {
        return new PacketByteBuffer.Builder()
            .build();
    }

    public static PacketByteBuffer test() {
        return new PacketByteBuffer.Builder()
            .writeMessage("Hello World!")
            .writeTime(LocalDate.now())
            .build();
    }
}