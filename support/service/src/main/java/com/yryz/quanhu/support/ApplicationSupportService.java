package com.yryz.quanhu.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationSupportService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSupportService.class, args);
    }
}
