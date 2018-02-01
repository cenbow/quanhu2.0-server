package com.yryz.quanhu.user.utils;

import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.Constants;
import com.yryz.quanhu.user.contants.RegType;

public class UserUtils {
	private static final String CHAR_95 = "_";
	private static final String RAND_STR = "0123456789abcdefghijklmnopqrstuvwxyz";
	 /**
     * 获取随机字符串
     */
    public static String randomappId() {
		String str = RAND_STR;
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
		return String.format("%s//%s",array[0], array[2].replaceAll("www", "api2"));
	}
	
	/**
	 * 
	 * 根据web应用host得到第三方登录回调的api host<br/>
	 * 因为微博认证时只能取应用下面域的地址才有效，所以需要根据访问域得到回调域，需要运维映射该域到我们平台
	 * 
	 * @param returnUrl
	 * @return
	 */
	public static String getReturnOauthApiHost(String returnUrl) {
		String[] array = returnUrl.split("/");		
		return String.format("%s//%s",array[0], array[2].replaceAll("m", "api2"));
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
	
	/**
	 * 从第三方登录state字段中获取登录方式
	 * @param state
	 * @return
	 */
	public static String getThirdLoginType(String state){
		String[] stateArray = StringUtils.split(state,CHAR_95);
		if(stateArray.length < 1){
			return null;
		}
		return stateArray[0];
	}
	
	/**
	 * 从第三方登录state字段中获取登录成功后重定向的地址
	 * @param state
	 * @return
	 */
	public static String getThirdLoginReturnUrl(String state){
		String[] stateArray = StringUtils.split(state,CHAR_95);
		if(stateArray.length < 2){
			return null;
		}
		return stateArray[1];
	}
	
	/**
	 * 从第三方登录state字段中获取活动渠道码
	 * @param state
	 * @return
	 */
	public static String getActivityChannelCode(String state){
		String[] stateArray = StringUtils.split(state,CHAR_95);
		if(stateArray.length < 3){
			return "";
		}
		return stateArray[2];
	}
}
