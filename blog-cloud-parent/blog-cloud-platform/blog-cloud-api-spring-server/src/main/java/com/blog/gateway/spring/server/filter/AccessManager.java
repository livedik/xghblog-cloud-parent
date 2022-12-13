package com.blog.gateway.spring.server.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;


// 身份认证成功后  对你的token 进行鉴权 有没有对这接口的访问权限 有则放行 无则GG

@Slf4j
@Component
public class AccessManager implements ReactiveAuthorizationManager<AuthorizationContext> {


//    方法名都叫check啦 很明显了 检查权限
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerWebExchange exchange = authorizationContext.getExchange();
        String path = exchange.getRequest().getURI().getPath();

        return authentication.map(a ->{
            return new AuthorizationDecision(checkAuthority(exchange,a,path));
        }).defaultIfEmpty(new AuthorizationDecision(false));

    }

//    从你数据库查这账号的权限 跟访问的接口权限对比 有就返回TRUE  没有就FASLE 就这样
    private boolean checkAuthority(ServerWebExchange exchange, Authentication a, String path) {
//        拿到权限列表
        Collection<? extends GrantedAuthority> authorities = a.getAuthorities();

//        管理员直接放行
        if (authorities.contains(new SimpleGrantedAuthority("admin"))) return true;

        String pathAuthority = getAuthoritys(path);

        if (authorities.contains(new SimpleGrantedAuthority(pathAuthority))) return true;

        return false;
    }

    // TODO 暂时简单设一下  后续从数据库中查
    private String getAuthoritys(String path) {

        return "123456";
    }
}
