/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月5日
 * Id: Jackson2ObjectMapperBuilder.java, 2018年2月5日 下午4:21:47 yehao
 */
package com.yryz.quanhu.openapi.mapper;

import java.text.SimpleDateFormat;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年2月5日 下午4:21:47
 * @Description 圈乎的JSON序列化
 */
@Configuration
public class QuanhuJackson2ObjectMapperBuilder {
	
	@Bean
    public Jackson2ObjectMapperBuilderCustomizer customJackson() {
        return new Jackson2ObjectMapperBuilderCustomizer() {

			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
//                jacksonObjectMapperBuilder.failOnUnknownProperties(false);
//                jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
                jacksonObjectMapperBuilder.dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			}

        };
    }

}
