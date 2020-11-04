package io.github.kimmking.gateway.outbound.netty4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.util.Map;

@Slf4j
public class NettyHttpClient {

    private HttpRequestParam param;
    private NettyHttpInboundHandler handler;

    public NettyHttpClient(HttpRequestParam param) {
        this.param = param;
        handler = new NettyHttpInboundHandler();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.group(worker).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new HttpClientCodec());
                    p.addLast(new HttpContentDecompressor());//这里要添加解压，不然打印时会乱码
                    p.addLast(new HttpObjectAggregator(123433));//添加HttpObjectAggregator， HttpClientMsgHandler才会收到FullHttpResponse
//                    p.addLast(new HttpServerResponseHandler());
                    p.addLast(handler);
                }
            });
            URI uri = param.getUri();
            ChannelFuture future = b.connect(uri.getHost(), uri.getPort()).sync();
            DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, param.getHttpMethod(), uri.getPath());
            defaultFullHttpRequest.headers().set(HttpHeaderNames.HOST, uri.getHost());
            defaultFullHttpRequest.headers().setAll(param.getHttpHeaders());

            if (future.isSuccess()) {
                future.channel().writeAndFlush(defaultFullHttpRequest);
            }

            //注册关闭事件
            future.channel().closeFuture().sync();

            worker.shutdownGracefully();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public String send() {
        return handler.getResult();
    }

    public static void main(String[] args) {
        HttpHeaders instance = new DefaultHttpHeaders();
        HttpRequestParam requestParam = HttpRequestParam.builder().uri(URI.create("http://localhost:8888/api/hello")).httpMethod(HttpMethod.GET).httpHeaders(instance).build();
        NettyHttpClient client = new NettyHttpClient(requestParam);
        String send = client.send();
        System.out.println(send);
    }
}
