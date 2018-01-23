package com.yryz.quanhu.support;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ImportResource("classpath*:spring-beans.xml")
public class ApplicationSupportService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationSupportService.class, args);
    }
}
