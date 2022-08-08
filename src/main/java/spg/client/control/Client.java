package spg.client.control;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import spg.client.ClientGui;
import spg.client.control.network.ClientAuthHandler;
import spg.client.control.network.ClientNetwork;
import spg.shared.network.ClientConnection;
import spg.shared.network.NetworkSide;
import spg.shared.network.PacketDecoder;
import spg.shared.network.PacketEncoder;
import spg.shared.network.c2s.ServerPublicKeyC2SPacket;

public class Client {
	private final String host;
	private final int port;

	private Bootstrap bootstrap;

	public static void main(String... args) {
		String host = args.length > 0
			? args[0]
			: "localhost";

		int port = args.length > 1
			? Integer.parseInt(args[1])
			: 8080;

		ClientGui.INSTANCE.initialize();
		Client client= new Client(host, port);
		client.bootstrap= new Bootstrap();
		client.start();	//TODO: start client after ip is present (-> after welcomeView)
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ClientConnection connection = new ClientConnection(NetworkSide.CLIENT);
		try{
				this.bootstrap.group(workerGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void channelActive(ChannelHandlerContext ctx) {
						System.out.println("Connected to server");
					}

					@Override
					public void channelInactive(ChannelHandlerContext ctx) {
						System.out.println("Disconnected from server");
					}

					@Override
					public void initChannel(SocketChannel ch) {
						ClientNetwork.INSTANCE.initialize();
						ClientNetwork.INSTANCE.connection = connection;
						connection.setListener(new ClientAuthHandler(connection));
						ch.pipeline().addLast(
							new PacketDecoder(NetworkSide.SERVER),
							new PacketEncoder(NetworkSide.CLIENT),
							connection
						);
					}

					@Override
					public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
						System.err.println("Error whilst connecting to server: " + cause.getMessage());
					}
				})
				.connect(host, port)
				.syncUninterruptibly().addListener(future -> {
					System.out.println("Requesting server public key...");
					ClientNetwork.INSTANCE.connection.send(
						new ServerPublicKeyC2SPacket()
					);
				})

				.channel()
				.closeFuture()
				.syncUninterruptibly();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
