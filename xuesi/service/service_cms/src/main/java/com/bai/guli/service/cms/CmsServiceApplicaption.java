package com.bai.guli.service.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.annotation.ApplicationScope;

@SpringBootApplication
@ComponentScan("com.bai.guli")
@EnableDiscoveryClient  //注册 服务
@EnableFeignClients  //远程调用

public class CmsServiceApplicaption {
    public static void main(String[] args) {
        SpringApplication.run(CmsServiceApplicaption.class, args);
    }
}
