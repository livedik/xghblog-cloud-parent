package com.xghblog.admin.server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/***
 * 平台认证服务  微服务 oauth2.0 统一认证
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UaaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(UaaAdminApplication.class,args);
    }
}
