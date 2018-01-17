package com.yryz.quanhu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ImportResource("classpath:META-INF/spring.xml")
public class ApplicationDemoService {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemoService.class, args);
    }
}
