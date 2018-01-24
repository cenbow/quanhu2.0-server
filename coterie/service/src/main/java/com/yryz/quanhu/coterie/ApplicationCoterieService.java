package com.yryz.quanhu.coterie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;
        import org.springframework.transaction.annotation.EnableTransactionManagement;

// 为了在dubbo impl服务中直接使用@Transactional注解 proxyTargetClass必须为true
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement(proxyTargetClass = true)
@ImportResource("classpath:META-INF/spring.xml")
public class ApplicationCoterieService {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationCoterieService.class, args);
    }
}
