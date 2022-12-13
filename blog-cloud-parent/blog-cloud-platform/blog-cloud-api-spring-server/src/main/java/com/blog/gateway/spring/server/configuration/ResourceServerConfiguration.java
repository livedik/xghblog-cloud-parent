package com.blog.gateway.spring.server.configuration;

import com.blog.gateway.spring.server.exception.JsonAuthenticationEntryPoint;
import com.blog.gateway.spring.server.filter.AccessManager;
import com.blog.gateway.spring.server.oauth2.JwtAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
import org.springframework.security.web.server.context.SecurityContextServerWebExchange;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfiguration{

    private static final String MAX_AGE = "18000L";

    @Autowired
    private AccessManager accessManager;

    @Autowired
    private ServerAccessDeniedHandler serverAccessDeniedHandler;

    @Autowired
    private JwtAuthenticationManager jwtAuthenticationManager;

    @Autowired
    private JsonAuthenticationEntryPoint entryPoint;

    /**
     * 跨域配置
     *
     * @return
     */
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders headers = response.getHeaders();
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
                headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
                if (requestMethod != null) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
                }
                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
                headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http){
//        认证过滤器 利用认证管理器进行令牌校验
        AuthenticationWebFilter oauth2 = new AuthenticationWebFilter(jwtAuthenticationManager);
        oauth2.setServerAuthenticationConverter(new ServerBearerTokenAuthenticationConverter());
        oauth2.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(entryPoint));

        oauth2.setAuthenticationSuccessHandler(new ServerAuthenticationSuccessHandler() {
            @Override
            public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
                ServerWebExchange exchange = webFilterExchange.getExchange();
                SecurityContextServerWebExchange securityContextServerWebExchange = new SecurityContextServerWebExchange(exchange, ReactiveSecurityContextHolder.getContext().subscriberContext(
                        ReactiveSecurityContextHolder.withAuthentication(authentication)
                ));
                return webFilterExchange.getChain().filter(securityContextServerWebExchange);
            }
        });

        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/").permitAll()
                // 动态权限验证
                .anyExchange().access(accessManager)
                .and().exceptionHandling()
                .accessDeniedHandler(serverAccessDeniedHandler)
                .authenticationEntryPoint(entryPoint)
                .and()
                // 跨域过滤器
//                .addFilterAt(corsFilter(),SecurityWebFiltersOrder.CORS)
                // oauth2认证过滤器
                .addFilterAt(oauth2, SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
}
