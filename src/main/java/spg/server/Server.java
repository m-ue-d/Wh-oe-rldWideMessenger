package spg.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {

	public static void main(String... args){
		int port = args.length > 0
		  ? Integer.parseInt(args[0])
		  : 8080;

		start(port);
	}

	public static void start(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			new ServerBootstrap()
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.handler(new ChannelInitializer<>() {
					@Override
					public void initChannel(Channel ch) {
						ch.pipeline().addLast(new ChannelHandlerAdapter() {
							@Override
							public void channelActive(ChannelHandlerContext ctx) {
								ServerNetwork.initialize();
								System.out.println("Server active!");
							}

							@Override
							public void channelInactive(ChannelHandlerContext ctx) throws Exception {
								System.out.println("Server inactive!");
							}
						});
					}
				})
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					public void initChannel(SocketChannel ch) {
						ch.pipeline().addLast(new ServerHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.bind(port).sync()
				.channel().closeFuture().sync();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}
