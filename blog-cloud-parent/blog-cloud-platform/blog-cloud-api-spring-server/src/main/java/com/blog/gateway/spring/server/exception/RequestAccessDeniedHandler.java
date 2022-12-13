package com.blog.gateway.spring.server.exception;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/***
 *  自定义 鉴权失败 、 没有权限 定制结果
 */
@Component
public class RequestAccessDeniedHandler implements ServerAccessDeniedHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, AccessDeniedException e) {
        ServerHttpResponse response = exchange.getResponse();

        response.setStatusCode(HttpStatus.METHOD_NOT_ALLOWED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap("没权限啊傻逼!".getBytes(
                Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer));
    }
}
