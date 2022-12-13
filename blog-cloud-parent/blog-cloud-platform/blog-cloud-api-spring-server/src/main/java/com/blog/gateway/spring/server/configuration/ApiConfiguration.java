package com.blog.gateway.spring.server.configuration;

import com.blog.gateway.spring.server.locator.JdbcRouteDefinitionLocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.InMemoryRouteDefinitionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@Configuration
public class ApiConfiguration {


    /**
     * 动态路由加载
     *
     * @return
     */
    @Bean
    public JdbcRouteDefinitionLocator jdbcRouteDefinitionLocator(JdbcTemplate jdbcTemplate, InMemoryRouteDefinitionRepository repository) {
        JdbcRouteDefinitionLocator jdbcRouteDefinitionLocator = new JdbcRouteDefinitionLocator(jdbcTemplate, repository);
        return jdbcRouteDefinitionLocator;
    }

}
