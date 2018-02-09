/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2017年10月12日
 * Id: LogInterceptor.java, 2017年10月12日 下午1:46:07 yehao
 */
package com.yryz.quanhu.openapi.interceptor;


import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.log.RequestContext;
import com.yryz.quanhu.openapi.service.RequestLogService;
import com.yryz.quanhu.support.requestlog.entity.RequestLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月12日 下午1:46:07
 * @Description 日志请求拦截器
 */
public class LogInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RequestLogService requestLogService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String httpMethod = request.getMethod();
        if (RequestMethod.OPTIONS.name().equals(httpMethod)) { //OPTIONS直接返回，而不记录
            return true;
        }
        RequestLog requestLog = RequestContext.getRequestLog();
        requestLog.setStartTime(System.currentTimeMillis());
        requestLog.setMethod(request.getMethod());
        requestLog.setRequest(GsonUtils.parseJson(request.getParameterMap()));
        requestLog.setRemoteName(request.getRemoteUser());
        requestLog.setRequestIp(WebUtil.getClientIP(request));
        requestLog.setUri(request.getRequestURI());
        requestLog.setHeader(GsonUtils.parseJson(getHeaderMap(request)));
        requestLog.setRequestTime(DateUtils.currentDatetime());
        return true;
    }


    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            String value = request.getHeader(name);
            map.put(name, value);
        }
        return map;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        String httpMethod = request.getMethod();
        if (RequestMethod.OPTIONS.name().equals(httpMethod)) {
            return;
        }
        RequestLog requestLog = RequestContext.getRequestLog();
        long startTime = requestLog.getStartTime() == null ? 0 : requestLog.getStartTime().longValue();
        long now = System.currentTimeMillis();
        requestLog.setEndTime(now);
        if (startTime != 0) {
            requestLog.setCountTime(now - startTime);
        }
        if (ex != null) {
            requestLog.setErrorMessage(getExceptionAllinformation(ex));
        }
        logger.info(GsonUtils.parseJson(requestLog));
        try {
            requestLogService.executeLog(requestLog);
        } catch (Exception e) {
            logger.warn("executeLog faild ...", e);
        }
    }

    public static String getExceptionAllinformation(Exception ex) {
        StringBuffer sb = new StringBuffer();
        sb.append("Exception: " + ex.getClass().getName() + " " + ex.getMessage() + "\r\n");
        StackTraceElement[] trace = ex.getStackTrace();
        for (StackTraceElement s : trace) {
            sb.append("\tat " + s + "\r\n");
        }
        return sb.toString();
    }

    /**
     * 获取异常堆栈信息
     *
     * @param t
     * @return
     */
    public String getStackTrace(Throwable t) {
        Writer out = new StringWriter();
        PrintWriter pw = new PrintWriter(out);
        t.printStackTrace(pw);
        pw.flush();
        try {
            out.flush();
        } catch (IOException e) {
            logger.debug("getStackTrace faild", e);
        }
        return out.toString();
    }

}
