package spg.client.control;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import spg.shared.security.RSA;

import java.math.BigInteger;
import java.net.Socket;

public class Client {
	private final String host;
	private final int port;
	private final BigInteger privateKey;
	public final BigInteger publicKey;
	public final BigInteger N;

	public static void main(String[] args) {
		ClientNetwork.initialize();
		// new Client("192.164.135.65", 6666).start();
		new Client("localhost", 8080).start();
	}

	public Client(String host, int port) {
		BigInteger[] temp= RSA.genKeyPair_plusN();
		this.privateKey=temp[0];
		this.publicKey=temp[1];
		this.N= temp[2];

		this.host = host;
		this.port = port;
	}

	public void start() {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		/*try {
			/*Socket socket = new Socket(this.host, this.port);
			System.out.println("Connected to: " + socket.getInetAddress());

			new SocketReader(socket, this).start();
			new SocketWriter(socket, this).start();



		} catch (Exception e) {
			System.err.println("Could not connect to server");
		}*/
		try{
			Bootstrap b = new Bootstrap(); // (1)
			b.group(workerGroup); // (2)
			b.channel(NioSocketChannel.class); // (3)
			b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ClientHandler());
				}
			});

			// Start the client.
			ChannelFuture f = b.connect(host, port).sync(); // (5)

			// Wait until the connection is closed.
			f.channel().closeFuture().sync();
		}
		catch (InterruptedException e){
			e.printStackTrace();
		}
		finally {
			workerGroup.shutdownGracefully();
		}
	}
	public byte[] decrypt(byte[] p){  //key==d
		return (new BigInteger(p)).modPow(privateKey,N).toByteArray();
	}

}
