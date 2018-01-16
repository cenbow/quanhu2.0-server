package com.yryz.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtils {

    public static boolean matcher(Object obj, String regEx) {
        if(obj == null || regEx == null){
            return false;
        }
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(obj.toString());
        return matcher.matches();
    }


    /**
     * 检测邮箱地址是否合法
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        //Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
