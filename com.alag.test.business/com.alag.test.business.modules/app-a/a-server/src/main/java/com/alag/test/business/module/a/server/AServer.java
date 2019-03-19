package com.alag.test.business.module.a.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableFeignClients(basePackages = {"com.alag.test.business.module.b.feign.com.alag.test.business.module.message.feign.controller"})
@EnableEurekaClient
@SpringBootApplication
@EnableTransactionManagement
public class AServer {
    public static void main(String[] args) {
        SpringApplication.run(AServer.class, args);
    }
}
