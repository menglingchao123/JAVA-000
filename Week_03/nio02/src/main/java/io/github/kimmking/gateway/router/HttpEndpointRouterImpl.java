package io.github.kimmking.gateway.router;

import io.github.kimmking.gateway.outbound.netty4.HttpRequestParam;
import io.github.kimmking.gateway.outbound.netty4.NettyHttpClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.util.CharsetUtil;
import jdk.internal.joptsimple.internal.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

public class HttpEndpointRouterImpl extends ChannelOutboundHandlerAdapter implements HttpEndpointRouter {

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
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
        NettyHttpClient client = new NettyHttpClient(build((fullHttpRequest)));
        String content = client.send();
        if (!Strings.isNullOrEmpty(content)) {
            ctx.writeAndFlush(content);
        }
    }

    private HttpRequestParam build(FullHttpRequest fullHttpRequest) {
        URI uri = URI.create(route(endpoints) + fullHttpRequest.uri());
        HttpHeaders headers = fullHttpRequest.headers();
        HttpRequestParam.Body body = HttpRequestParam.Body.builder().args(fullHttpRequest.content().toString(CharsetUtil.UTF_8)).build();
        return HttpRequestParam.builder().httpHeaders(headers).uri(uri).httpMethod(fullHttpRequest.method()).body(body).build();
    }

    @Override
    public String route(List<String> endpoints) {
        if (endpoints != null && endpoints.isEmpty()) {
            int size = endpoints.size();
            Random random = new Random(size);
            return endpoints.get(random.nextInt());
        }
        return null;
    }
}
