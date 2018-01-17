package com.yryz.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.yryz.common.exception.ParseDatesException;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2017 2017/11/15 16:32
 * @Author: pn
 */
public class BeanUtils {

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
     * 
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object target, Object source) 
    		throws ParseDatesException {
    	if (target == null || source == null) {  
            return;  
        }  
    	
		try {
			org.springframework.beans.BeanUtils.copyProperties(source, target);
			//BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			throw new ParseDatesException("copyProperties", e.getCause());
		}
    }
    
    /**
     * 
     * @param dest
     * @param orig
     */
    public static void copyProperties(Object dest, Object orig,String... ignore) 
    		throws ParseDatesException {
    	if (dest == null || orig == null) {  
            return;  
        }  
    	
		try {
			org.springframework.beans.BeanUtils.copyProperties(orig, dest,ignore);
			//BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			throw new ParseDatesException("copyProperties", e.getCause());
		}
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
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
       /* Map<String, String> map = new HashMap<>();
        map.put("kid","1");
        Test test = getBean(Test.class, map);
        System.out.println(test.getKid());*/

       String s = "15327533357";
        String s1 = s.substring(s.length() - 9, s.length());
        System.out.println(s1);
    }
}
