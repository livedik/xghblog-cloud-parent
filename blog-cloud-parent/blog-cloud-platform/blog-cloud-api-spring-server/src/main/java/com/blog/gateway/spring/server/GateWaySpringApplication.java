package com.blog.gateway.spring.server;

import com.blog.gateway.spring.server.locator.JdbcRouteDefinitionLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@RemoteApplicationEventScan(basePackages = "com.blog")
public class GateWaySpringApplication implements CommandLineRunner {


    @Autowired
    private JdbcRouteDefinitionLocator jdbcRouteDefinitionLocator;

    public static void main(String[] args) {
        SpringApplication.run(GateWaySpringApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcRouteDefinitionLocator.refresh();
    }
}
