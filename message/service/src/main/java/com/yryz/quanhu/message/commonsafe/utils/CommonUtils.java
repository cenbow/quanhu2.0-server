/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月20日
 * Id: CommonUtils.java, 2017年11月20日 下午2:26:25 Administrator
 */
package com.yryz.quanhu.message.commonsafe.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月20日 下午2:26:25
 */
public class CommonUtils {
	private static final String ALL_CHAR_STR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
     * 获取某个位数的随机字符串
     * @param num
     * @return
     */
    public static String getRandomStr(int num){
    	Random random = new Random();
    	StringBuffer buffer = new StringBuffer();
    	for(int i = 0 ; i < num ; i++){
    		//获取随机字符
			char ch = ALL_CHAR_STR.charAt(random.nextInt(ALL_CHAR_STR.length()));
			buffer.append(ch);		
    	}
    	return buffer.toString();
    }
    
    public static String getRandomNum(int num) {
			StringBuffer sbf = new StringBuffer("");
			Random rand = new Random();
			for (int i = 0; i < num; i++) {
				sbf.append(rand.nextInt(10));
			}
			return sbf.toString();
		}
    
    /**
     * 获取指定字符串出现的次数
     * @param srcText
     * @param findText
     * @return
     */
    public static int getNumInStr(String srcText,String findText){
    	int count = 0;
    	Pattern pattern = Pattern.compile(findText);
    	Matcher m = pattern.matcher(srcText);
    	while(m.find()){
    		count++;
    	}
    	return count;
    }
}
