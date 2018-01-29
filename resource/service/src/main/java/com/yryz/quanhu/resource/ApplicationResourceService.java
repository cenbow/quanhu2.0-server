package com.yryz.quanhu.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
@ImportResource("classpath:META-INF/spring.xml")
public class ApplicationResourceService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationResourceService.class, args);
    }
}
