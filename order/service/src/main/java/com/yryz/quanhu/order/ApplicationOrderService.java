package com.yryz.quanhu.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationOrderService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationOrderService.class, args);
    }
}
