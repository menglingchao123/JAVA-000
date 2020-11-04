package io.github.kimmking.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyHttpInboundHandler extends SimpleChannelInboundHandler<FullHttpResponse> {

    private String content;
    private ChannelPromise promise;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpResponse response) throws Exception {
        content = response.content().toString(Charset.forName("utf-8"));
        System.out.println("收到服务端返还结果========>"+content);
    }

    public String getResult() {
        return content;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("解析netty服务响应数据出现异常=========>", cause);
        ctx.close();
    }
}
