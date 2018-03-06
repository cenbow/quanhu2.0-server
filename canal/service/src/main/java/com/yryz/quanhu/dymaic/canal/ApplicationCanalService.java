package com.yryz.quanhu.dymaic.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ImportResource("classpath:META-INF/spring.xml")
@EnableDiscoveryClient
@EnableTransactionManagement(proxyTargetClass = true)
public class ApplicationCanalService {
	public static void main(String[] args) {
		SpringApplication.run(ApplicationCanalService.class, args);
	}
}
