package com.yryz.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Utils {
    public static String encode(String s) {  
        if (s == null) {  
            return null;  
        }
        return DigestUtils.md5Hex(s);
    }  
    
    public static void main(String[]args){
    	String passwd =  "123456";  
        System.out.println(passwd + " 加密后为： " + encode(passwd)) ;
    }
}
