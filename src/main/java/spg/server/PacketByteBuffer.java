package spg.server;

import spg.shared.security.AES;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

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
	private HashMap<String, byte[]> userKeys = new HashMap<String,byte[]>();	//befüllt mit den Namen der User(müssen daher vom server eindeutig verifiziert werden: sonst keine verbindung!   ----    und die dazu verschlüsselten versionen des threadkeys)

	public PacketByteBuffer(byte[] content) {//solten auch die reciever im Konstruktor sein

		byte[] x= content;

		AES aes= new AES();
		String key = AES.genKey();
		x= aes.encrypt(Arrays.toString(content),key).getBytes();	//tatsächliche Verschlüsselung des Contents

		//Hashmap befüllen

		/*
		for (ClientMain client:receivers) { //hashmap befüllen
			byte[] encodedThreadKey = RSA.encode(key, client.publicKey, client.N);
			userKeys.put(client.getUsername(), encodedThreadKey);
		}
		*/


		this.content = x;
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
