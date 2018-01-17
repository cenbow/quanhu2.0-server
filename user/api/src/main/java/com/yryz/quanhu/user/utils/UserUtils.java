package com.yryz.quanhu.user.utils;

import com.yryz.quanhu.user.contants.Constants;
import com.yryz.quanhu.user.contants.RegType;

public class UserUtils {
	 /**
     * 获取随机字符串
     */
    public static String randomappId() {
		String str = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuffer custId = new StringBuffer("");
		for (int i = 0; i < 4; i++) {
			int j = (int) (35 * Math.random());
			custId.append(str.charAt(j));
		}
		return custId.toString();
	}
    
	/**
	 * 
	 * 根据web应用host得到第三方登录回调的api host<br/>
	 * 因为微博认证时只能取应用下面域的地址才有效，所以需要根据访问域得到回调域，需要运维映射该域到我们平台
	 * 
	 * @param returnUrl
	 * @return
	 */
	public static String getReturnApiHost(String returnUrl) {
		String[] array = returnUrl.split("/");		
		return String.format("%s//%s",array[0], array[2].replaceAll("www", "api"));
	}
	
	/**
	 * 获取第三方应用appKey
	 * @param appId
	 * @param regType
	 * @return
	 */
	public static String getThirdAppKey(String appId,RegType regType){
		return String.format("%s.%s.%s",regType.getText(),appId, Constants.APP_KEY);
	}
}
