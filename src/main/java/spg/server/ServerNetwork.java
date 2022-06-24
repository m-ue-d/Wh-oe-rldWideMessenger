package spg.server;

public class ServerNetwork {
	public static void sendPacket(UserThread user, Packet packet) {
		user.send(packet);
	}
}
