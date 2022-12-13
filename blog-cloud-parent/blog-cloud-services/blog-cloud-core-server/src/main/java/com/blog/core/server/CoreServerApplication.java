package com.blog.core.server;


/***
 * 流程引擎服务器
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/***
 * 核心业务服务器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.blog"})
@MapperScan(basePackages = {"com.blog.core.server.mapper"})
public class CoreServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreServerApplication.class,args);
    }
}
