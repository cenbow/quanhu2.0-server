package com.yryz.quanhu.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yryz.common.utils.StringUtils;

public class PhoneUtils {
	/**
	 * 三大运营商手机号匹配正则表达式
	 */
	private static final String PHONE_REGEX = "^(13[0-9]|15[012356789]|16[6]|17[0135678]|18[0-9]|14[56789]|19[89])[0-9]{8}$";
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
	
	/**
	 * 得到中间4位隐藏的手机号
	 * 
	 * @param phone
	 * @return
	 */
	public static String getPhone(String phone) {
		if (StringUtils.isBlank(phone)) {
			return phone;
		}
		return String.format("%s****%s", StringUtils.substring(phone, 0, 3), StringUtils.substring(phone, 7));
	}

}
