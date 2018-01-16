package com.yryz.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class PatternUtils {
	
	/**
	 * 三大运营商手机号匹配正则表达式
	 */
	private static final String PHONE_REGEX = "^(13[0-9]|15[012356789]|16[6]|17[0135678]|18[0-9]|14[56789]|19[89])[0-9]{8}$";


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
    
	/**
	 * 验证手机号码是否有效
	 * @param phone
	 * @return
	 */
	public static boolean checkPhone(String phone){
		if(StringUtils.isBlank(phone)){
			return false;
		}
		Pattern pattern = Pattern.compile(PHONE_REGEX);
		Matcher matcher = pattern.matcher(phone);
		return matcher.matches();
	}

}
