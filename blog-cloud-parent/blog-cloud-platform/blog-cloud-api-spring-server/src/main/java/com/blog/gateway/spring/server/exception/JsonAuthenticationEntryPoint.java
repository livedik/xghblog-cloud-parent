package com.blog.gateway.spring.server.exception;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/***
 *  自定义 令牌过期啊 没登陆啊 失效啊 那些错误返回信息
 */
@Component
public class JsonAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {


    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        return Mono.defer(() -> Mono.just(exchange.getResponse())).flatMap(response -> {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            DataBufferFactory dataBufferFactory = response.bufferFactory();
//            String result = JSONObject.toJSONString(ResultVoUtil.failed(UserStatusCodeEnum.USER_UNAUTHORIZED));
            DataBuffer buffer = dataBufferFactory.wrap("没令牌!".getBytes(
                    Charset.defaultCharset()));
            return response.writeWith(Mono.just(buffer));
        });

    }
}
