package com.blog.file.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/***
 * 附件服务器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.blog"})
@MapperScan(basePackages = {"com.blog.file.server.mapper"})
public class FileServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(FileServerApplication.class,args);
    }

}
