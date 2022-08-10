package spg.client.control;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import spg.client.view.ClientGui;
import spg.client.control.network.ClientAuthHandler;
import spg.client.control.network.ClientNetwork;
import spg.shared.network.ClientConnection;
import spg.shared.network.NetworkSide;
import spg.shared.network.PacketDecoder;
import spg.shared.network.PacketEncoder;
import spg.shared.network.c2s.ServerKeyC2SPacket;

public class Client {
	private final String host;
	private final int port;

	public static void main(String... args) {
		String host = args.length > 0
			? args[0]
			: "localhost";

		int port = args.length > 1
			? Integer.parseInt(args[1])
			: 8080;

		Client client= new Client(host, port);
		client.start();
	}

	public Client(String host, int port) {
		ClientGui.INSTANCE.initialize();
		this.host = host;
		this.port = port;
	}

	public void start() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			new Bootstrap()
				.group(workerGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					public void channelInactive(ChannelHandlerContext ctx) {
						System.out.println("Disconnected from server");
					}

					@Override
					public void initChannel(SocketChannel ch) {
						ClientNetwork.INSTANCE.initialize();
						var connection = new ClientConnection(NetworkSide.CLIENT);
						ClientNetwork.INSTANCE.connection = connection;
						connection.setListener(new ClientAuthHandler(connection));

						ch.pipeline().addLast(
							new PacketEncoder(NetworkSide.CLIENT),
							new PacketDecoder(NetworkSide.SERVER),
							connection
						);
					}
				})
				.connect(host, port)
				.syncUninterruptibly().addListener(future -> { // Runs when connecting is done
					if (future.isSuccess()) {
						System.out.println("Connected to server");
						System.out.println("Requesting server public key...");
						ClientNetwork.INSTANCE.connection.send(
							new ServerKeyC2SPacket()
						);
					} else {
						System.err.println("Failed to connect to server: " + future.cause().getMessage());
					}
				})

				.channel()
				.closeFuture()
				.syncUninterruptibly();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
