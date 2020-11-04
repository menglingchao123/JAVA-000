package io.github.kimmking.gateway.outbound.netty4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyHttpInboundHandler extends ChannelInboundHandlerAdapter {

    private String content;
    private ChannelPromise promise;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        promise = ctx.newPromise();
        if (msg instanceof HttpRequest) {
            HttpRequest httpRequest = (HttpRequest) msg;
            DecoderResult decoderResult = httpRequest.decoderResult();
            if (decoderResult.isFailure()) {
                return;
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf buf = httpContent.content();
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            content = new String(bytes, "UTF-8");
            promise.setSuccess();
            ctx.writeAndFlush(content);
        }
    }

    public synchronized String getResult() {
        try {
            while (promise == null) {
                TimeUnit.MILLISECONDS.sleep(2);
            }
            promise.await(5,TimeUnit.MILLISECONDS);
            return content;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("解析netty服务响应数据出现异常=========>", cause);
        ctx.close();
    }
}
