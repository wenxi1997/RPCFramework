package client;

import core.RpcRequest;
import core.RpcResponse;
import handler.RpcDecoder;
import handler.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author jony
 */
public class Rpclient extends SimpleChannelInboundHandler<RpcResponse> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Rpclient.class);

    private String address;

    private int port;

    private Channel channel;

    private volatile CountDownLatch latch;

    public Rpclient(String address, int port) {
        this.address = address;
        this.port = port;
        init();
    }

    private void init() {
        try {
            init0();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void init0() throws Exception{
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new RpcEncoder(RpcRequest.class));
                        pipeline.addLast(new RpcDecoder(RpcResponse.class));
                        pipeline.addLast(Rpclient.this);
                    }
                });
        ChannelFuture future = bootstrap.connect(address, port).sync();
        this.channel = future.channel();
    }

    public RpcResponse send(RpcRequest request) {
        latch = new CountDownLatch(1);
        channel.writeAndFlush(request);
        try {
            latch.await();
        } catch (InterruptedException e) {}
        return channel.attr(AttributeKey.<RpcResponse>valueOf(request.getId())).get();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse msg) {
        channel.attr(AttributeKey.valueOf(msg.getId())).set(msg);
        latch.countDown();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.info("Something's wrong");
        ctx.close();
    }

}
