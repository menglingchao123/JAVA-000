package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.outbound.netty4.HttpRequestParam;
import io.github.kimmking.gateway.outbound.netty4.NettyHttpClient;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpEndpointRouterImpl extends ChannelInboundHandlerAdapter implements HttpEndpointRouter {

    private static List<String> endpoints;

    static {
        try {
            InputStream inStream = HttpEndpointRouterImpl.class.getClassLoader().getResourceAsStream("endpoint.properties");
            Properties prop = new Properties();
            prop.load(inStream);
            Collection<Object> values = prop.values();
            endpoints = values.stream().map(x -> (String) x).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        NettyHttpClient client = new NettyHttpClient(build((fullHttpRequest)));
        String content = client.send();
        System.out.println("网关收到服务器返回数据========>" + content);
        if (content != null) {
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", content.getBytes().length);
            ctx.writeAndFlush(response);
        }
    }

    private HttpRequestParam build(FullHttpRequest fullHttpRequest) {
        URI uri = URI.create("http://"+route(endpoints) + fullHttpRequest.uri());
        HttpHeaders headers = fullHttpRequest.headers();
        HttpRequestParam.Body body = HttpRequestParam.Body.builder().args(fullHttpRequest.content().toString(CharsetUtil.UTF_8)).build();
        return HttpRequestParam.builder().httpHeaders(headers).uri(uri).httpMethod(fullHttpRequest.method()).body(body).build();
    }

    @Override
    public String route(List<String> endpoints) {
        if (endpoints != null && !endpoints.isEmpty()) {
            int size = endpoints.size();
            Random random = new Random();
            int index = random.nextInt(size);
            return endpoints.get(index);
        }
        return null;
    }
}
