package com.yryz.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.yryz.common.exception.ParseDatesException;

/**
 * @version 1.0
 * @Date 2018年1月18日 10:01:04
 * @Author xiepeng
 */
public class BeanUtils {

    /**
     * 对象属性值拷贝
     * @param target
     * @param source
     * @throws ParseDatesException
     */
    public static void copyProperties(Object target, Object source)
            throws ParseDatesException {
        if (target == null || source == null) {
            return;
        }

        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new ParseDatesException("copyProperties", e.getCause());
        }
    }

    /**
     * 对象属性值拷贝
     * @param target
     * @param source
     * @param ignore 忽略属性
     * @throws ParseDatesException
     */
    public static void copyProperties(Object target, Object source,String... ignore)
            throws ParseDatesException {
        if (target == null || source == null) {
            return;
        }

        try {
            org.springframework.beans.BeanUtils.copyProperties(source, target,ignore);
        } catch (Exception e) {
            throw new ParseDatesException("copyProperties", e.getCause());
        }
    }

    /**
     * bean转换为Map
     * @param obj
     * @return
     * @throws ParseDatesException
     */
    public static Map<String, String> toMap(Object obj)
            throws ParseDatesException {

        if(obj == null){
            return null;
        }
        Map<String, String> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    if (value != null) {
                        map.put(key, value.toString());
                    }
                }
            }
        } catch (Exception e) {
            throw new ParseDatesException("toMap", e.getCause());
        }

        return map;
    }

    /**
     * 得到bean为空的属性
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }


    public static <T> T getBean(Class<T> clazz, Map map) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        if (clazz != null && map != null) {
            Method[] methods = clazz.getMethods();
            T t = clazz.newInstance();
            for (Object key : map.keySet()) {
                for (Method method : methods) {
                    if (method.getName().startsWith("set")) {
                        String fieldName = method.getName().substring(3);
                        fieldName = fieldName.toLowerCase();
                        if (key.equals(fieldName)) {
                            String typeName= map.get(key).getClass().getTypeName();
                            if (typeName.equals("String")) {
                                method.invoke(t, (String)map.get(key));
                            }else if(typeName.equals("Long")){

                            }
                        }
                    }
                }
            }
            return t;
        }
        return null;
    }

    /**
     * 得到Object 属性不为NULL,值的分割 串（缓存key，使用）
     * @param source
     * @return
     */
    public static String getNotNullPropertyValue(Object source, String split) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        for (java.beans.PropertyDescriptor pd : pds) {
            if("class".equals(pd.getName())){
                continue;
            }
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) {
                if (i != 0) {
                    sb.append(split);
                }
                sb.append(srcValue.toString());
                ++i;
            }
        }
        if (StringUtils.isNotBlank(sb.toString())) {
            return sb.toString();
        }

        return null;
    }
}
