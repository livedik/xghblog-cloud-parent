package com.blog.admin.server.configuration;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {


    @Override
    public void configure(HttpSecurity http) throws Exception {
        //禁用session，JWT校验不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()//允许基于使用HttpServletRequest限制访问
                .antMatchers("/**","/login/**","/oauth/**").permitAll() // 放行路径 需要认证的路径 登录那些 登录还要认证的话搞个毛
                // 监控端点内部放行
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/logout").permitAll()
                .and()
                .logout().permitAll()
                // /logout退出清除cookie
                .addLogoutHandler(new CookieClearingLogoutHandler("token", "remember-me"))
                .and()
                .csrf().disable()
                // 禁用httpBasic
                .httpBasic().disable();
    }

}
