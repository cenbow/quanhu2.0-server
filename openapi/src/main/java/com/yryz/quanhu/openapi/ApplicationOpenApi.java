package com.yryz.quanhu.openapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages={"com.yryz.quanhu"})
@EnableDiscoveryClient
@EnableSwagger2
public class ApplicationOpenApi extends SpringBootServletInitializer {
    public static final String CURRENT_VERSION = "v2";
    public static final String COMPATIBLE_VERSION = "v2,v1";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationOpenApi.class);
    }
    
    @Bean
    public Docket docket(@Value("${swagger.enable:true}")boolean enableSwagger) {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(
                        new ApiInfoBuilder()
                                .title("圈乎2.0")
                                .description("当前API版本" + CURRENT_VERSION + " 兼容API版本" + COMPATIBLE_VERSION)
                                .version(CURRENT_VERSION)
                                .build())
                .select().apis(RequestHandlerSelectors.basePackage(ApplicationOpenApi.class.getPackage().getName())).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationOpenApi.class, args);
    }

}
