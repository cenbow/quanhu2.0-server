/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月20日
 * Id: PhoneUtils.java, 2017年11月20日 上午9:40:54 Administrator
 */
package com.yryz.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月20日 上午9:40:54
 * @Description 手机号处理工具类
 */
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
	
	public static void main(String[] args){
		String ss = "12345434545";
		System.out.println(ss.hashCode());
	}
}
