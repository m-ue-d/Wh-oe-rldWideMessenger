package spg.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	private final int port;
	private final ArrayList<UserThread> connectedUsers = new ArrayList<>();

	public static void main(String[] args){
		new Server(8080).start();
	}

	public Server(int port) {
		this.port = port;
	}

	public void start(){
		/*try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Listening on port: " + port);
			//noinspection InfiniteLoopStatement
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("User connected: " + socket.getInetAddress());

				UserThread connectedUser = new UserThread(socket, this);
				connectedUsers.add(connectedUser);
				connectedUser.start();
			}
		} catch (Exception e) {
			System.err.println("Could not start server: " + e.getMessage());
		}*/

		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class) // (3)
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ch.pipeline().addLast(new ServerHandler());
						}
					})
					.option(ChannelOption.SO_BACKLOG, 128)          // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

	public void broadcast(Packet packet) {
		for (UserThread connectedUser : connectedUsers) {
			ServerNetwork.sendPacket(connectedUser, packet);
		}
	}
}
