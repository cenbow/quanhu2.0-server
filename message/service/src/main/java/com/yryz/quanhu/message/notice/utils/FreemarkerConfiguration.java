package com.yryz.quanhu.message.notice.utils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/2/26 9:34
 * @Author: pn
 */
@Configuration
public class FreemarkerConfiguration {

    @Bean
    public freemarker.template.Configuration freemarkerConfig() throws IOException {
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_27);
        cfg.setTemplateLoader(new ClassTemplateLoader(getClass().getClassLoader(), "/templates"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        return cfg;
    }

}
