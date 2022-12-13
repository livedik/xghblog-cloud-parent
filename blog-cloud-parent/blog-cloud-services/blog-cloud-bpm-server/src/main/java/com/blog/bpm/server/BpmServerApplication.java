package com.blog.bpm.server;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/***
 * 流程引擎服务器
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.blog"})
@MapperScan(basePackages = {"com.blog.bpm.server.mapper"})
public class BpmServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(BpmServerApplication.class,args);
    }
}

