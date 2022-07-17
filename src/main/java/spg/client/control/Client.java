package spg.client.control;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import spg.client.control.network.ClientChatHandler;
import spg.shared.network.ClientConnection;
import spg.shared.network.NetworkSide;
import spg.shared.network.PacketDecoder;
import spg.shared.network.PacketEncoder;

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

		new Client(host, port).start();
	}

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ClientConnection connection = new ClientConnection(NetworkSide.CLIENT);
		try{
			new Bootstrap()
				.group(workerGroup)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
						ClientNetwork.connection = connection;
						connection.setListener(new ClientChatHandler(connection));
						ch.pipeline().addLast(
							new PacketDecoder(NetworkSide.SERVER),
							new PacketEncoder(NetworkSide.CLIENT),
							connection
						);
					}
				})
				.connect(host, port)
				.syncUninterruptibly()

				.channel()
				.closeFuture()
				.syncUninterruptibly();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}
}
