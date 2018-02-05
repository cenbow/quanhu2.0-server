package com.yryz.quanhu.message;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationMessageService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMessageService.class, args);
    }
}