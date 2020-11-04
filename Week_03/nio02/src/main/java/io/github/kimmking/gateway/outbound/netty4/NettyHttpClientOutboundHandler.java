package io.github.kimmking.gateway.outbound.netty4;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;

public class NettyHttpClientOutboundHandler  extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        //获取请求参数
        HttpRequestParam request = (HttpRequestParam) msg;
        //封装http请求参数
        String args = request.getBody().getArgs();
        HttpMethod httpMethod = request.getHttpMethod();
        URI uri = request.getUri();
        DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,httpMethod,uri.getPath(), Unpooled.wrappedBuffer(args.getBytes("UTF-8")));
        //写入数据
        ctx.writeAndFlush(defaultFullHttpRequest);
    }

}