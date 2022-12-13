package com.blog.gateway.spring.server.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// 令牌认证管理器  检验你令牌是否有效啊 过期之类的 身份认证
@Slf4j
@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private TokenStore tokenStore;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((token -> {
                    OAuth2AccessToken accessToken = this.tokenStore.readAccessToken(token);
                    if (accessToken==null)
                    {
                        return Mono.error(new InvalidTokenException("无效的token"));
                    }else if (accessToken.isExpired())
                    {
                        return Mono.error(new InvalidTokenException("token过期了"));
                    }
                    OAuth2Authentication oAuth2Authentication = this.tokenStore.readAuthentication(token);
                    if(oAuth2Authentication==null){
                        return Mono.error(new InvalidTokenException("无效的token"));
                    }else{
                        return Mono.just(oAuth2Authentication);
                    }
                }))
                .cast(Authentication.class);
    }
}
