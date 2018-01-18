package com.yryz.quanhu.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)   // 为了在dubbo impl服务中直接使用@Transactional注解 proxyTargetClass必须为true
@ImportResource("classpath:META-INF/spring.xml")
public class ApplicationDemoService {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationDemoService.class, args);
    }
}
