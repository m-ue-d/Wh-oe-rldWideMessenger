package spg.client.control;

import spg.server.Packet;

import java.util.ArrayList;
import java.util.List;

public class ClientNetwork {
	public record PacketRegistryEntry(String identifier, PacketReceiveHandler handler) {

	}

	public interface PacketReceiveHandler {
		void handle(Client client, byte[] data);
	}

	private static final List<PacketRegistryEntry> entries = new ArrayList<>();

	public static void registerPacket(String identifier, PacketReceiveHandler handler) {
		entries.add(
			new PacketRegistryEntry(identifier, handler)
		);
	}

	public static void handlePacket(Client client, Packet packet) {
		for (PacketRegistryEntry entry : entries) {
			if (entry.identifier.equals(packet.identifier)) {
				entry.handler.handle(client, packet.content.getContent());
				return;
			}
		}
	}

	public static void initialize() {
		registerPacket("UserMessage", (client, data) -> {
			System.out.println(
				new String(data)
			);
		});
	}
}
