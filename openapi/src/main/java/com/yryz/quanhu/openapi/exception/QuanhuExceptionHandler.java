package com.yryz.quanhu.openapi.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.openapi.validation.BehaviorEventValidAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 18:34
 * Created by huangxy
 *
 * 前端统一异常渲染器
 */
@Component
public class QuanhuExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(QuanhuExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response,
                                         Object o, Exception e) {

        try {
            response.setStatus(HttpStatus.OK.value()); //设置状态码
            response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
            response.setCharacterEncoding("UTF-8"); //避免乱码
//            String language = request.getHeader("language");
            response.getWriter().write(JSON.toJSONString(ResponseUtils.returnException(e), SerializerFeature.WriteMapNullValue));
            logger.error("【ExceptionHandler】", e);

        } catch (Exception _e) {
            logger.error("处理异常失败", _e);
        }
        return new ModelAndView();
    }
}
