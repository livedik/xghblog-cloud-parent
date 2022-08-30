package com.xghblog.services.demoSpring;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.xghblog.services.demoSpring.mapper")
public class DemoSpringboot {

    public static void main(String[] args) {
        SpringApplication.run(DemoSpringboot.class);
    }
}
