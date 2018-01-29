package com.yryz.quanhu.other;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// 为了在dubbo impl服务中直接使用@Transactional注解 proxyTargetClass必须为true
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@ImportResource("classpath:META-INF/spring.xml")
public class ApplicationOtherService {


    public static void main(String[] args) {
        SpringApplication.run(ApplicationOtherService.class, args);
    }
}
