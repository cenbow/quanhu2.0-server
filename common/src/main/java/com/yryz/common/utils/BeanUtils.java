package com.yryz.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

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
