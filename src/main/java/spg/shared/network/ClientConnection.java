package spg.shared.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class ClientConnection extends SimpleChannelInboundHandler<Packet<?>> {

    public static final AttributeKey<NetworkState> PROTOCOL_KEY = AttributeKey.valueOf("protocol");

    private final NetworkSide side;
    private Channel channel;
    private PacketListener listener;

    private String verificationCode;
    private Runnable onVerificationCode;

    public ClientConnection(NetworkSide side) {
        this.side = side;
    }

    public NetworkSide getSide() {
        return side;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setState(NetworkState state) {
        channel.attr(PROTOCOL_KEY).set(state);
    }

    public NetworkState getState() {
        return channel.attr(PROTOCOL_KEY).get();
    }

    public void setListener(PacketListener listener) {
        this.listener = listener;
    }

    public PacketListener getListener() {
        return listener;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setOnVerificationSuccess(Runnable onSuccess) {
        this.onVerificationCode = onSuccess;
    }

    public void onVerificationCode() {
        onVerificationCode.run();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channel = ctx.channel();
        channel.attr(PROTOCOL_KEY).set(
            NetworkState.AUTHENTICATION
        );
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (channel.isOpen()) {
            channel.close().awaitUninterruptibly();
        }
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Packet<?> msg) {
        handle(msg, listener);
    }

    @SuppressWarnings("unchecked")
    private static <T extends PacketListener> void handle(Packet<T> packet, PacketListener listener) {
        try {
            packet.apply((T) listener);
        } catch (Exception e) {
            System.err.println("ClientConnection: Error whilst handling packet, are you sure you have set the correct listener?");
            e.printStackTrace();
        }
    }

    public ChannelFuture send(Packet<?> packet) {
        if (channel != null) {
            return channel.writeAndFlush(packet).addListener( future -> {
                if (!future.isSuccess()) {
                    System.err.println("Failed to send packet: " + packet.getClass().getSimpleName());
                    future.cause().printStackTrace();
                }
            });
        } else {
            System.err.println("ClientConnection: Channel is null");
            return null;
        }
    }
}
