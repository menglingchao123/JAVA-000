package io.github.kimmking.gateway.outbound.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
@Slf4j
public class NettyHttpClient {

    private HttpRequestParam param;
    private NettyHttpInboundHandler handler;
    private Channel channel;

    public NettyHttpClient(HttpRequestParam param) {
        this.param = param;
        handler = new NettyHttpInboundHandler();
        NioEventLoopGroup worker = new NioEventLoopGroup(10);
        try {
            Bootstrap b = new Bootstrap();
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                            .addLast(new HttpResponseDecoder())
                            .addLast(handler)
                            .addLast(new HttpRequestEncoder())
                            .addLast(new NettyHttpClientOutboundHandler());
                }
            });
            URI uri = param.getUri();
            ChannelFuture future = b.connect(uri.getHost(), uri.getPort()).sync();
            future.channel().writeAndFlush(param);
            //注册连接事件
            future.addListener((ChannelFutureListener)x -> {
                //如果连接成功
                if (x.isSuccess()) {
                    log.info("客户端[" + x.channel().localAddress().toString() + "]已连接...");
                    channel = x.channel();
                }
                //如果连接失败，尝试重新连接
                else{
                    log.info("客户端[" + x.channel().localAddress().toString() + "]连接失败，重新连接中...");
                    x.channel().close();
                    b.connect(uri.getHost(), uri.getPort());
                }
            });

            //注册关闭事件
            future.channel().closeFuture().addListener(cfl -> {
                //关闭客户端套接字
                if(channel!=null){
                    channel.close();
                }
                //关闭客户端线程组
                if (worker != null) {
                    worker.shutdownGracefully();
                }
                log.info("客户端[" + future.channel().localAddress().toString() + "]已断开...");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public String send(){
        channel.writeAndFlush(param);
        return handler.getResult();
    }
}
