package com.yryz.quanhu.openapi.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 10:36
 * Created by huangxy
 */
@Service
public class BehaviorArgsBuild {

    public static final String PREFIX_REQUEST_HEAD = "request.head.";
    public static final String PREFIX_REQUEST_FORM = "request.form.";
    public static final String PREFIX_MAP = "map.";
    public static final String PREFIX_OBJECT = "object.";


    /**
     * 重新替换值，暂不支持request中替换，只支持map，和对象
     * @param annotationKey
     * @param joinPointArgs
     * @param newValue
     */
    public void replaceParameterValue(String annotationKey,Object[] joinPointArgs,Object newValue){
        int lastKey = annotationKey.lastIndexOf(".");
        //获取取值key
        String key = annotationKey.substring(lastKey+1,annotationKey.length());

        //通过参数map 获取值
        if(annotationKey.startsWith(PREFIX_MAP)){
            Map<String,Object> map = (Map<String, Object>) this.getObjByClass(Map.class,joinPointArgs);
            map.put(key,newValue);
            return;
        }

        //通过参数bean  get方法 获取值
        if(annotationKey.startsWith(PREFIX_OBJECT)){
            //获取实例名
            String className = annotationKey.substring(PREFIX_OBJECT.length(),lastKey);
            try {
                //获取实例
                Object instance = this.getObjByName(className,joinPointArgs);

                //通过get获取参数
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(key,instance.getClass());
                propertyDescriptor.getWriteMethod().invoke(instance,newValue);
                return;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //均不匹配，则抛出异常
        throw new RuntimeException("replaceParameterValue con't supports type :"+annotationKey);
    }


    /**
     * 获取值
     * @param annotationKey
     * @param joinPointArgs
     * @return
     */
    public Object getParameterValue(String annotationKey, Object[] joinPointArgs){

        if(StringUtils.isBlank(annotationKey)){
            return null;
        }

        int lastKey = annotationKey.lastIndexOf(".");
        //获取取值key
        String key = annotationKey.substring(lastKey+1,annotationKey.length());

        //通过request head 获取值
        if(annotationKey.startsWith(PREFIX_REQUEST_HEAD)){
            HttpServletRequest request = (HttpServletRequest) getObjByClass(HttpServletRequest.class,joinPointArgs);
            if(request==null){
                request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            }
            return request.getHeader(key);
        }

        //通过request form 提交 获取值
        if(annotationKey.startsWith(PREFIX_REQUEST_FORM)){
            HttpServletRequest request = (HttpServletRequest) getObjByClass(HttpServletRequest.class,joinPointArgs);
            if(request==null){
                request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            }
            return request.getParameter(key);
        }

        //通过参数map 获取值
        if(annotationKey.startsWith(PREFIX_MAP)){
            Map<String,Object> map = (Map<String, Object>) this.getObjByClass(Map.class,joinPointArgs);
            return map.get(key);
        }

        //通过参数bean  get方法 获取值
        if(annotationKey.startsWith(PREFIX_OBJECT)){
            //获取实例名
            String className = annotationKey.substring(PREFIX_OBJECT.length(),lastKey);
            try {
                //获取实例
                Object instance = this.getObjByName(className,joinPointArgs);

                //通过get获取参数
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(key,instance.getClass());
                return propertyDescriptor.getReadMethod().invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //均不匹配，则抛出异常
        throw new RuntimeException("getParameterValue con't supports type :"+annotationKey);
    }

    public <T> Object getObjByClass(Class<T> objClass,Object[] joinPointArgs){
        for(int i = 0 ; i<joinPointArgs.length ;i++){
            Object obj = joinPointArgs[i];
            if(obj!=null && objClass.isAssignableFrom(obj.getClass())){
                return obj;
            }
        }
        return null;
    }

    public <T> Object getObjByName(String className,Object[] joinPointArgs){
        for(int i = 0 ; i<joinPointArgs.length ;i++){
            Object obj = joinPointArgs[i];
            if(obj!=null && obj.getClass().getName().lastIndexOf(className)!=-1){
                return obj;
            }
        }
        return null;
    }

}
