package spg.server;

import spg.shared.security.AES;
import spg.shared.security.RSA;

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

		public PacketByteBuffer build(int[] ids) {
			return new PacketByteBuffer(
				content.toString().getBytes(),
					ids
			);
		}
	}

	private final byte[] content;
	private final HashMap<String, byte[]> userKeys = new HashMap<>(); //befüllt mit den ids der User(müssen daher vom server eindeutig verifiziert werden: sonst keine verbindung!   ----    und die dazu verschlüsselten versionen des threadkeys)

	public PacketByteBuffer(byte[] content, int[] ids) {

		byte[] x= content;

		String key = AES.genKey();
		x= AES.encrypt(Arrays.toString(content),key).getBytes();	//tatsächliche Verschlüsselung des Contents

		//Hashmap befüllen

		//im server brauche ich noch eine Möglichkeit, den public Key eines verbundenen Users per id zu erhalten

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
			.build(null);
	}

	public static PacketByteBuffer test() {
		return new PacketByteBuffer.Builder()
			.writeMessage("Hello World!")
			.writeTime(LocalDate.now())
			.build(null);
	}
}
