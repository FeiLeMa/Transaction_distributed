package com.alag.test.business.module.b.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class BFeign {
    public static void main(String[] args) {
        SpringApplication.run(BFeign.class, args);
    }
}
