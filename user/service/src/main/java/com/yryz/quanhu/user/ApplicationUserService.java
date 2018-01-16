package com.yryz.quanhu.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ApplicationUserService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationUserService.class, args);
    }
}
