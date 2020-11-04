package io.github.kimmking.gateway.outbound.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpRequestParam {
    private URI uri;
    private HttpMethod httpMethod;
    private HttpHeaders httpHeaders;
    private Body body;
//    private ChannelHandlerContext ctx;

    @Data
    @Builder
    public static class Body{
        private String args;
    }
}
